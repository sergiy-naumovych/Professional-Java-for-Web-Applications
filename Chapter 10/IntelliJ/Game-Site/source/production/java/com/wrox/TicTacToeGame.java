package com.wrox;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class TicTacToeGame
{
    private static long gameIdSequence = 1L;
    private static final Hashtable<Long, String> pendingGames = new Hashtable<>();
    private static final Map<Long, TicTacToeGame> activeGames = new Hashtable<>();
    private final long id;
    private final String player1;
    private final String player2;
    private Player nextMove = Player.random();
    private Player[][] grid = new Player[3][3];
    private boolean over;
    private boolean draw;
    private Player winner;

    private TicTacToeGame(long id, String player1, String player2)
    {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    public long getId()
    {
        return id;
    }

    public String getPlayer1()
    {
        return player1;
    }

    public String getPlayer2()
    {
        return player2;
    }

    public String getNextMoveBy()
    {
        return nextMove == Player.PLAYER1 ? player1 : player2;
    }

    public boolean isOver()
    {
        return over;
    }

    public boolean isDraw()
    {
        return draw;
    }

    public Player getWinner()
    {
        return winner;
    }

    @JsonIgnore
    public synchronized void move(Player player, int row, int column)
    {
        if(player != this.nextMove)
            throw new IllegalArgumentException("It is not your turn!");

        if(row > 2 || column > 2)
            throw new IllegalArgumentException("Row and column must be 0-3.");

        if(this.grid[row][column] != null)
            throw new IllegalArgumentException("Move already made at " + row + ","
                    + column);

        this.grid[row][column] = player;
        this.nextMove =
                this.nextMove == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.calculateWinner();
        if(this.getWinner() != null || this.isDraw())
            this.over = true;
        if(this.isOver())
            TicTacToeGame.activeGames.remove(this.id);
    }

    public synchronized void forfeit(Player player)
    {
        TicTacToeGame.activeGames.remove(this.id);
        this.winner = player == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.over = true;
    }

    private Player calculateWinner()
    {
        boolean draw = true;
        // horizontal wins
        for(int i = 0; i < 3; i++)
        {
            if(this.grid[i][0] == null || this.grid[i][1] == null ||
                    this.grid[i][2] == null)
                draw = false;
            if(this.grid[i][0] != null && this.grid[i][0] == this.grid[i][1] &&
                    this.grid[i][0] == this.grid[i][2])
                return this.grid[i][0];
        }

        // vertical wins
        for(int i = 0; i < 3; i++)
        {
            if(this.grid[0][i] != null && this.grid[0][i] == this.grid[1][i] &&
                    this.grid[0][i] == this.grid[2][i])
                return this.grid[0][i];
        }

        // diagonal wins
        if(this.grid[0][0] != null && this.grid[0][0] == this.grid[1][1] &&
                this.grid[0][0] == this.grid[2][2])
            return this.grid[0][0];
        if(this.grid[0][2] != null && this.grid[0][2] == this.grid[1][1] &&
                this.grid[0][2] == this.grid[2][0])
            return this.grid[0][2];

        this.draw = draw;

        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<Long, String> getPendingGames()
    {
        return (Map<Long, String>)TicTacToeGame.pendingGames.clone();
    }

    public static long queueGame(String user1)
    {
        long id = TicTacToeGame.gameIdSequence++;
        TicTacToeGame.pendingGames.put(id, user1);
        return id;
    }

    public static void removeQueuedGame(long queuedId)
    {
        TicTacToeGame.pendingGames.remove(queuedId);
    }

    public static TicTacToeGame startGame(long queuedId, String user2)
    {
        String user1 = TicTacToeGame.pendingGames.remove(queuedId);
        TicTacToeGame game = new TicTacToeGame(queuedId, user1, user2);
        TicTacToeGame.activeGames.put(queuedId, game);
        return game;
    }

    public static TicTacToeGame getActiveGame(long gameId)
    {
        return TicTacToeGame.activeGames.get(gameId);
    }

    public enum Player
    {
        PLAYER1, PLAYER2;

        private static final Random random = new Random();

        private static Player random()
        {
            return Player.random.nextBoolean() ? PLAYER1 : PLAYER2;
        }
    }
}
