<%--@elvariable id="action" type="java.lang.String"--%>
<%--@elvariable id="gameId" type="long"--%>
<%--@elvariable id="username" type="java.lang.String"--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Game Site :: Tic Tac Toe</title>
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
        <link rel="stylesheet"
              href="<c:url value="/resource/stylesheet/ticTacToe.css" />" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <h2>Tic Tac Toe</h2>
        <span class="player-label">You:</span> ${username}<br />
        <span class="player-label">Opponent:</span>
        <span id="opponent"><i>Waiting</i></span>
        <div id="status">&nbsp;</div>
        <div id="gameContainer">
            <div class="row">
                <div id="r0c0" class="game-cell" onclick="move(0, 0);">&nbsp;</div>
                <div id="r0c1" class="game-cell" onclick="move(0, 1);">&nbsp;</div>
                <div id="r0c2" class="game-cell" onclick="move(0, 2);">&nbsp;</div>
            </div>
            <div class="row">
                <div id="r1c0" class="game-cell" onclick="move(1, 0);">&nbsp;</div>
                <div id="r1c1" class="game-cell" onclick="move(1, 1);">&nbsp;</div>
                <div id="r1c2" class="game-cell" onclick="move(1, 2);">&nbsp;</div>
            </div>
            <div class="row">
                <div id="r2c0" class="game-cell" onclick="move(2, 0);">&nbsp;</div>
                <div id="r2c1" class="game-cell" onclick="move(2, 1);">&nbsp;</div>
                <div id="r2c2" class="game-cell" onclick="move(2, 2);">&nbsp;</div>
            </div>
        </div>
        <div id="modalWaiting" class="modal hide fade">
            <div class="modal-header"><h3>Please Wait...</h3></div>
            <div class="modal-body" id="modalWaitingBody">&nbsp;</div>
        </div>
        <div id="modalError" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3>Error</h3>
            </div>
            <div class="modal-body" id="modalErrorBody">A blah error occurred.
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
        <div id="modalGameOver" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;
                </button>
                <h3>Game Over</h3>
            </div>
            <div class="modal-body" id="modalGameOverBody">&nbsp;</div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
        <script type="text/javascript" language="javascript">
            var move;
            $(document).ready(function() {
                var modalError = $("#modalError");
                var modalErrorBody = $("#modalErrorBody");
                var modalWaiting = $("#modalWaiting");
                var modalWaitingBody = $("#modalWaitingBody");
                var modalGameOver = $("#modalGameOver");
                var modalGameOverBody = $("#modalGameOverBody");
                var opponent = $("#opponent");
                var status = $("#status");
                var opponentUsername;
                var username = '<c:out value="${username}" />';
                var myTurn = false;

                $('.game-cell').addClass('span1');

                if(!("WebSocket" in window))
                {
                    modalErrorBody.text('WebSockets are not supported in this ' +
                            'browser. Try Internet Explorer 10 or the latest ' +
                            'versions of Mozilla Firefox or Google Chrome.');
                    modalError.modal('show');
                    return;
                }

                modalWaitingBody.text('Connecting to the server.');
                modalWaiting.modal({ keyboard: false, show: true });

                var server;
                try {
                    server = new WebSocket('ws://' + window.location.host +
                            '<c:url value="/ticTacToe/${gameId}/${username}">
                                <c:param name="action" value="${action}" />
                            </c:url>');
                } catch(error) {
                    modalWaiting.modal('hide');
                    modalErrorBody.text(error);
                    modalError.modal('show');
                    return;
                }

                server.onopen = function(event) {
                    modalWaitingBody
                            .text('Waiting on your opponent to join the game.');
                    modalWaiting.modal({ keyboard: false, show: true });
                };

                window.onbeforeunload = function() {
                    server.close();
                };

                server.onclose = function(event) {
                    if(!event.wasClean || event.code != 1000) {
                        toggleTurn(false, 'Game over due to error!');
                        modalWaiting.modal('hide');
                        modalErrorBody.text('Code ' + event.code + ': ' +
                                event.reason);
                        modalError.modal('show');
                    }
                };

                server.onerror = function(event) {
                    modalWaiting.modal('hide');
                    modalErrorBody.text(event.data);
                    modalError.modal('show');
                };

                server.onmessage = function(event) {
                    var message = JSON.parse(event.data);
                    if(message.action == 'gameStarted') {
                        if(message.game.player1 == username)
                            opponentUsername = message.game.player2;
                        else
                            opponentUsername = message.game.player1;
                        opponent.text(opponentUsername);
                        toggleTurn(message.game.nextMoveBy == username);
                        modalWaiting.modal('hide');
                    } else if(message.action == 'opponentMadeMove') {
                        $('#r' + message.move.row + 'c' + message.move.column)
                                .unbind('click')
                                .removeClass('game-cell-selectable')
                                .addClass('game-cell-opponent game-cell-taken');
                        toggleTurn(true);
                    } else if(message.action == 'gameOver') {
                        toggleTurn(false, 'Game Over!');
                        if(message.winner) {
                            modalGameOverBody.text('Congratulations, you won!');
                        } else {
                            modalGameOverBody.text('User "' + opponentUsername +
                                    '" won the game.');
                        }
                        modalGameOver.modal('show');
                    } else if(message.action == 'gameIsDraw') {
                        toggleTurn(false, 'The game is a draw. ' +
                                'There is no winner.');
                        modalGameOverBody.text('The game ended in a draw. ' +
                                'Nobody wins!');
                        modalGameOver.modal('show');
                    } else if(message.action == 'gameForfeited') {
                        toggleTurn(false, 'Your opponent forfeited!');
                        modalGameOverBody.text('User "' + opponentUsername +
                                '" forfeited the game. You win!');
                        modalGameOver.modal('show');
                    }
                };

                var toggleTurn = function(isMyTurn, message) {
                    myTurn = isMyTurn;
                    if(myTurn) {
                        status.text(message || 'It\'s your move!');
                        $('.game-cell:not(.game-cell-taken)')
                                .addClass('game-cell-selectable');
                    } else {
                        status.text(message ||'Waiting on your opponent to move.');
                        $('.game-cell-selectable')
                                .removeClass('game-cell-selectable');
                    }
                };

                move = function(row, column) {
                    if(!myTurn) {
                        modalErrorBody.text('It is not your turn yet!');
                        modalError.modal('show');
                        return;
                    }
                    if(server != null) {
                        server.send(JSON.stringify({ row: row, column: column }));
                        $('#r' + row + 'c' + column).unbind('click')
                                .removeClass('game-cell-selectable')
                                .addClass('game-cell-player game-cell-taken');
                        toggleTurn(false);
                    } else {
                        modalErrorBody.text('Not connected to came server.');
                        modalError.modal('show');
                    }
                };
            });
        </script>
    </body>
</html>
