<%--@elvariable id="chatSessionId" type="long"--%>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat">
    <jsp:attribute name="extraHeadContent">
        <link rel="stylesheet"
              href="<c:url value="/resource/stylesheet/chat.css" />" />
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="chatContainer">
            <div id="chatLog">

            </div>
            <div id="messageContainer">
                <textarea id="messageArea"></textarea>
            </div>
            <div id="buttonContainer">
                <button class="btn btn-primary" onclick="send();">Send</button>
                <button class="btn" onclick="disconnect();">Disconnect</button>
            </div>
        </div>
        <div id="modalError" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Error</h3>
            </div>
            <div class="modal-body" id="modalErrorBody">A blah error occurred.</div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
        <script type="text/javascript" language="javascript">
            var send, disconnect;
            $(document).ready(function() {
                var modalError = $("#modalError");
                var modalErrorBody = $("#modalErrorBody");
                var chatLog = $('#chatLog');
                var messageArea = $('#messageArea');
                var username = '${sessionScope.username}';
                var otherJoined = false;

                if(!("WebSocket" in window)) {
                    modalErrorBody.text('WebSockets are not supported in this ' +
                            'browser. Try Internet Explorer 10 or the latest ' +
                            'versions of Mozilla Firefox or Google Chrome.');
                    modalError.modal('show');
                    return;
                }

                var infoMessage = function(m) {
                    chatLog.append($('<div>').addClass('informational')
                            .text(moment().format('h:mm:ss a') + ': ' + m));
                };
                infoMessage('Connecting to the chat server...');

                var objectMessage = function(message) {
                    var log = $('<div>');
                    var date = message.timestamp == null ? '' :
                            moment.unix(message.timestamp).format('h:mm:ss a');
                    if(message.user != null) {
                        var c = message.user == username ? 'user-me' : 'user-you';
                        log.append($('<span>').addClass(c)
                                        .text(date+' '+message.user+':\xA0'))
                                .append($('<span>').text(message.content));
                    } else {
                        log.addClass(message.type == 'ERROR' ? 'error' :
                                'informational')
                                .text(date + ' ' + message.content);
                    }
                    chatLog.append(log);
                };

                var server;
                try {
                    server = new WebSocket('ws://' + window.location.host +
                            '<c:url value="/chat/${chatSessionId}" />');
                    server.binaryType = 'arraybuffer';
                } catch(error) {
                    modalErrorBody.text(error);
                    modalError.modal('show');
                    return;
                }

                server.onopen = function(event) {
                    infoMessage('Connected to the chat server.');
                };

                server.onclose = function(event) {
                    if(server != null)
                        infoMessage('Disconnected from the chat server.');
                    server = null;
                    if(!event.wasClean || event.code != 1000) {
                        modalErrorBody.text('Code ' + event.code + ': ' +
                                event.reason);
                        modalError.modal('show');
                    }
                };

                server.onerror = function(event) {
                    modalErrorBody.text(event.data);
                    modalError.modal('show');
                };

                server.onmessage = function(event) {
                    if(event.data instanceof ArrayBuffer) {
                        var message = JSON.parse(String.fromCharCode.apply(
                                null, new Uint8Array(event.data)
                        ));
                        objectMessage(message);
                        if(message.type == 'JOINED') {
                            otherJoined = true;
                            if(username != message.user)
                                infoMessage('You are now chatting with ' +
                                        message.user + '.');
                        }
                    } else {
                        modalErrorBody.text('Unexpected data type [' +
                                typeof(event.data) + '].');
                        modalError.modal('show');
                    }
                };

                send = function() {
                    if(server == null) {
                        modalErrorBody.text('You are not connected!');
                        modalError.modal('show');
                    } else if(!otherJoined) {
                        modalErrorBody.text(
                                'The other user has not joined the chat yet.');
                        modalError.modal('show');
                    } else if(messageArea.get(0).value.trim().length > 0) {
                        var message = {
                            timestamp: new Date(), type: 'TEXT', user: username,
                            content: messageArea.get(0).value
                        };
                        try {
                            var json = JSON.stringify(message);
                            var length = json.length;
                            var buffer = new ArrayBuffer(length);
                            var array = new Uint8Array(buffer);
                            for(var i = 0; i < length; i++) {
                                array[i] = json.charCodeAt(i);
                            }
                            server.send(buffer);
                            messageArea.get(0).value = '';
                        } catch(error) {
                            modalErrorBody.text(error);
                            modalError.modal('show');
                        }
                    }
                };

                disconnect = function() {
                    if(server != null) {
                        infoMessage('Disconnected from the chat server.');
                        server.close();
                        server = null;
                    }
                };

                window.onbeforeunload = disconnect;
            });
        </script>
    </jsp:body>
</template:basic>
