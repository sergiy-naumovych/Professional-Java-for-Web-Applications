package com.wrox;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/ticTacToe/{gameId}/{username}")
public class TicTacToeServer
{
    private static Map<Long, Game> games = new Hashtable<>();
    private static ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") long gameId,
                       @PathParam("username") String username)
    {
        try
        {
            TicTacToeGame ticTacToeGame = TicTacToeGame.getActiveGame(gameId);
            if(ticTacToeGame != null)
            {
                session.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION,
                        "This game has already started."
                ));
            }

            List<String> actions = session.getRequestParameterMap().get("action");
            if(actions != null && actions.size() == 1)
            {
                String action = actions.get(0);
                if("start".equalsIgnoreCase(action))
                {
                    Game game = new Game();
                    game.gameId = gameId;
                    game.player1 = session;
                    TicTacToeServer.games.put(gameId, game);
                }
                else if("join".equalsIgnoreCase(action))
                {
                    Game game = TicTacToeServer.games.get(gameId);
                    game.player2 = session;
                    game.ticTacToeGame = TicTacToeGame.startGame(gameId, username);
                    this.sendJsonMessage(game.player1, game,
                            new GameStartedMessage(game.ticTacToeGame));
                    this.sendJsonMessage(game.player2, game,
                            new GameStartedMessage(game.ticTacToeGame));
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            try
            {
                session.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()
                ));
            }
            catch(IOException ignore) { }
        }
    }

    @OnMessage
    public void onMessage(Session session, String message,
                          @PathParam("gameId") long gameId)
    {
        Game game = TicTacToeServer.games.get(gameId);
        boolean isPlayer1 = session == game.player1;

        try
        {
            Move move = TicTacToeServer.mapper.readValue(message, Move.class);
            game.ticTacToeGame.move(
                    isPlayer1 ? TicTacToeGame.Player.PLAYER1 :
                            TicTacToeGame.Player.PLAYER2,
                    move.getRow(),
                    move.getColumn()
            );
            this.sendJsonMessage((isPlayer1 ? game.player2 : game.player1), game,
                    new OpponentMadeMoveMessage(move));
            if(game.ticTacToeGame.isOver())
            {
                if(game.ticTacToeGame.isDraw())
                {
                    this.sendJsonMessage(game.player1, game,
                            new GameIsDrawMessage());
                    this.sendJsonMessage(game.player2, game,
                            new GameIsDrawMessage());
                }
                else
                {
                    boolean wasPlayer1 = game.ticTacToeGame.getWinner() ==
                            TicTacToeGame.Player.PLAYER1;
                    this.sendJsonMessage(game.player1, game,
                            new GameOverMessage(wasPlayer1));
                    this.sendJsonMessage(game.player2, game,
                            new GameOverMessage(!wasPlayer1));
                }
                game.player1.close();
                game.player2.close();
            }
        }
        catch(IOException e)
        {
            this.handleException(e, game);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") long gameId)
    {
        Game game = TicTacToeServer.games.get(gameId);
        if(game == null)
            return;
        boolean isPlayer1 = session == game.player1;
        if(game.ticTacToeGame == null)
        {
            TicTacToeGame.removeQueuedGame(game.gameId);
        }
        else if(!game.ticTacToeGame.isOver())
        {
            game.ticTacToeGame.forfeit(isPlayer1 ? TicTacToeGame.Player.PLAYER1 :
                    TicTacToeGame.Player.PLAYER2);
            Session opponent = (isPlayer1 ? game.player2 : game.player1);
            this.sendJsonMessage(opponent, game, new GameForfeitedMessage());
            try
            {
                opponent.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void sendJsonMessage(Session session, Game game, Message message)
    {
        try
        {
            session.getBasicRemote()
                   .sendText(TicTacToeServer.mapper.writeValueAsString(message));
        }
        catch(IOException e)
        {
            this.handleException(e, game);
        }
    }

    private void handleException(Throwable t, Game game)
    {
        t.printStackTrace();
        String message = t.toString();
        try
        {
            game.player1.close(new CloseReason(
                    CloseReason.CloseCodes.UNEXPECTED_CONDITION, message
            ));
        }
        catch(IOException ignore) { }
        try
        {
            game.player2.close(new CloseReason(
                    CloseReason.CloseCodes.UNEXPECTED_CONDITION, message
            ));
        }
        catch(IOException ignore) { }
    }

    private static class Game
    {
        public long gameId;

        public Session player1;

        public Session player2;

        public TicTacToeGame ticTacToeGame;
    }

    public static class Move
    {
        private int row;

        private int column;

        public int getRow()
        {
            return row;
        }

        public void setRow(int row)
        {
            this.row = row;
        }

        public int getColumn()
        {
            return column;
        }

        public void setColumn(int column)
        {
            this.column = column;
        }
    }

    public static abstract class Message
    {
        private final String action;

        public Message(String action)
        {
            this.action = action;
        }

        public String getAction()
        {
            return this.action;
        }
    }

    public static class GameStartedMessage extends Message
    {
        private final TicTacToeGame game;

        public GameStartedMessage(TicTacToeGame game)
        {
            super("gameStarted");
            this.game = game;
        }

        public TicTacToeGame getGame()
        {
            return game;
        }
    }

    public static class OpponentMadeMoveMessage extends Message
    {
        private final Move move;

        public OpponentMadeMoveMessage(Move move)
        {
            super("opponentMadeMove");
            this.move = move;
        }

        public Move getMove()
        {
            return move;
        }
    }

    public static class GameOverMessage extends Message
    {
        private final boolean winner;

        public GameOverMessage(boolean winner)
        {
            super("gameOver");
            this.winner = winner;
        }

        public boolean isWinner()
        {
            return winner;
        }
    }

    public static class GameIsDrawMessage extends Message
    {
        public GameIsDrawMessage()
        {
            super("gameIsDraw");
        }
    }

    public static class GameForfeitedMessage extends Message
    {
        public GameForfeitedMessage()
        {
            super("gameForfeited");
        }
    }
}
