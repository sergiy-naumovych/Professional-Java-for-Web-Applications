package com.wrox.chat;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/chat/{sessionId}",
        encoders = ChatMessageCodec.class,
        decoders = ChatMessageCodec.class,
        configurator = ChatEndpoint.EndpointConfigurator.class)
@WebListener
public class ChatEndpoint implements HttpSessionListener
{
    private static final String HTTP_SESSION_PROPERTY = "com.wrox.ws.HTTP_SESSION";
    private static final String WS_SESSION_PROPERTY = "com.wrox.http.WS_SESSION";
    private static long sessionIdSequence = 1L;
    private static final Object sessionIdSequenceLock = new Object();
    private static final Map<Long, ChatSession> chatSessions = new Hashtable<>();
    private static final Map<Session, ChatSession> sessions = new Hashtable<>();
    private static final Map<Session, HttpSession> httpSessions =
            new Hashtable<>();
    public static final List<ChatSession> pendingSessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId)
    {
        HttpSession httpSession = (HttpSession)session.getUserProperties()
                .get(ChatEndpoint.HTTP_SESSION_PROPERTY);
        try
        {
            if(httpSession == null || httpSession.getAttribute("username") == null)
            {
                session.close(new CloseReason(
                        CloseReason.CloseCodes.VIOLATED_POLICY,
                        "You are not logged in!"
                ));
                return;
            }

            String username = (String)httpSession.getAttribute("username");
            session.getUserProperties().put("username", username);
            ChatMessage message = new ChatMessage();
            message.setTimestamp(OffsetDateTime.now());
            message.setUser(username);
            ChatSession chatSession;
            if(sessionId < 1)
            {
                message.setType(ChatMessage.Type.STARTED);
                message.setContent(username + " started the chat session.");
                chatSession = new ChatSession();
                synchronized(ChatEndpoint.sessionIdSequenceLock)
                {
                    chatSession.setSessionId(ChatEndpoint.sessionIdSequence++);
                }
                chatSession.setCustomer(session);
                chatSession.setCustomerUsername(username);
                chatSession.setCreationMessage(message);
                ChatEndpoint.pendingSessions.add(chatSession);
                ChatEndpoint.chatSessions.put(chatSession.getSessionId(),
                        chatSession);
            }
            else
            {
                message.setType(ChatMessage.Type.JOINED);
                message.setContent(username + " joined the chat session.");
                chatSession = ChatEndpoint.chatSessions.get(sessionId);
                chatSession.setRepresentative(session);
                chatSession.setRepresentativeUsername(username);
                ChatEndpoint.pendingSessions.remove(chatSession);
                session.getBasicRemote()
                        .sendObject(chatSession.getCreationMessage());
                session.getBasicRemote().sendObject(message);
            }

            ChatEndpoint.sessions.put(session, chatSession);
            ChatEndpoint.httpSessions.put(session, httpSession);
            this.getSessionsFor(httpSession).add(session);
            chatSession.log(message);
            chatSession.getCustomer().getBasicRemote().sendObject(message);
        }
        catch(IOException | EncodeException e)
        {
            this.onError(session, e);
        }
    }

    @OnMessage
    public void onMessage(Session session, ChatMessage message)
    {
        ChatSession c = ChatEndpoint.sessions.get(session);
        Session other = this.getOtherSession(c, session);
        if(c != null && other != null)
        {
            c.log(message);
            try
            {
                session.getBasicRemote().sendObject(message);
                other.getBasicRemote().sendObject(message);
            }
            catch(IOException | EncodeException e)
            {
                this.onError(session, e);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        if(reason.getCloseCode() == CloseReason.CloseCodes.NORMAL_CLOSURE)
        {
            ChatMessage message = new ChatMessage();
            message.setUser((String)session.getUserProperties().get("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " left the chat.");
            try
            {
                Session other = this.close(session, message);
                if(other != null)
                    other.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e)
    {
        ChatMessage message = new ChatMessage();
        message.setUser((String)session.getUserProperties().get("username"));
        message.setType(ChatMessage.Type.ERROR);
        message.setTimestamp(OffsetDateTime.now());
        message.setContent(message.getUser() + " left the chat due to an error.");
        try
        {
            Session other = this.close(session, message);
            if(other != null)
                other.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()
                ));
        }
        catch(IOException ignore) { }
        finally
        {
            try
            {
                session.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()
                ));
            }
            catch(IOException ignore) { }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event)
    {
        HttpSession httpSession = event.getSession();
        if(httpSession.getAttribute(WS_SESSION_PROPERTY) != null)
        {
            ChatMessage message = new ChatMessage();
            message.setUser((String)httpSession.getAttribute("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " logged out.");
            for(Session session:new ArrayList<>(this.getSessionsFor(httpSession)))
            {
                try
                {
                    session.getBasicRemote().sendObject(message);
                    Session other = this.close(session, message);
                    if(other != null)
                        other.close();
                }
                catch(IOException | EncodeException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        session.close();
                    }
                    catch(IOException ignore) { }
                }
            }
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) { /* do nothing */ }

    @SuppressWarnings("unchecked")
    private synchronized ArrayList<Session> getSessionsFor(HttpSession session)
    {
        try
        {
            if(session.getAttribute(WS_SESSION_PROPERTY) == null)
                session.setAttribute(WS_SESSION_PROPERTY, new ArrayList<>());

            return (ArrayList<Session>)session.getAttribute(WS_SESSION_PROPERTY);
        }
        catch(IllegalStateException e)
        {
            return new ArrayList<>();
        }
    }

    private Session close(Session s, ChatMessage message)
    {
        ChatSession c = ChatEndpoint.sessions.get(s);
        Session other = this.getOtherSession(c, s);
        ChatEndpoint.sessions.remove(s);
        HttpSession h = ChatEndpoint.httpSessions.get(s);
        if(h != null)
            this.getSessionsFor(h).remove(s);
        if(c != null)
        {
            c.log(message);
            ChatEndpoint.pendingSessions.remove(c);
            ChatEndpoint.chatSessions.remove(c.getSessionId());
            try
            {
                c.writeChatLog(new File("chat." + c.getSessionId() + ".log"));
            }
            catch(Exception e)
            {
                System.err.println("Could not write chat log.");
                e.printStackTrace();
            }
        }
        if(other != null)
        {
            ChatEndpoint.sessions.remove(other);
            h = ChatEndpoint.httpSessions.get(other);
            if(h != null)
                this.getSessionsFor(h).remove(s);
            try
            {
                other.getBasicRemote().sendObject(message);
            }
            catch(IOException | EncodeException e)
            {
                e.printStackTrace();
            }
        }
        return other;
    }

    private Session getOtherSession(ChatSession c, Session s)
    {
        return c == null ? null :
                (s == c.getCustomer() ? c.getRepresentative() : c.getCustomer());
    }

    public static class EndpointConfigurator
            extends ServerEndpointConfig.Configurator
    {
        @Override
        public void modifyHandshake(ServerEndpointConfig config,
                                    HandshakeRequest request,
                                    HandshakeResponse response)
        {
            super.modifyHandshake(config, request, response);

            config.getUserProperties().put(
                    ChatEndpoint.HTTP_SESSION_PROPERTY, request.getHttpSession()
            );
        }
    }
}
