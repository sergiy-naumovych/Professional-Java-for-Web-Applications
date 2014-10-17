package com.wrox.site.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class DefaultChatService implements ChatService
{
    private static final Logger log = LogManager.getLogger();

    private final Map<Long, ChatSession> sessions = new Hashtable<>();
    private final Map<Long, ChatSession> pendingSessions = new Hashtable<>();
    private final Map<Long, List<ChatMessage>> chatLogs = new Hashtable<>();
    private volatile long SESSION_ID_SEQUENCE = 1L;

    @Inject ObjectMapper objectMapper;

    @Override
    public CreateResult createSession(String user)
    {
        ChatMessage message = new ChatMessage();
        message.setUser(user);
        message.setTimestamp(Instant.now());
        message.setType(ChatMessage.Type.STARTED);
        message.setContent(user + " started the chat session.");

        ChatSession session = new ChatSession();
        session.setSessionId(this.getNextSessionId());
        session.setCustomerUsername(user);
        session.setCreationMessage(message);

        this.sessions.put(session.getSessionId(), session);
        this.pendingSessions.put(session.getSessionId(), session);
        this.chatLogs.put(session.getSessionId(), new ArrayList<>());
        this.logMessage(session, message);

        return new CreateResult(session, message);
    }

    @Override
    public JoinResult joinSession(long id, String user)
    {
        ChatSession session = this.pendingSessions.remove(id);
        if(session == null)
        {
            log.warn("Attempt to join non-existent session {}.", id);
            return null;
        }
        session.setRepresentativeUsername(user);

        ChatMessage message = new ChatMessage();
        message.setUser(user);
        message.setTimestamp(Instant.now());
        message.setType(ChatMessage.Type.JOINED);
        message.setContent(user + " joined the chat session.");
        this.logMessage(session, message);

        return new JoinResult(session, message);
    }

    @Override
    public ChatMessage leaveSession(ChatSession session, String user,
                                    ReasonForLeaving reason)
    {
        long id = session.getSessionId();
        this.pendingSessions.remove(id); // in case closed before support joined
        if(this.sessions.remove(id) == null)
            return null;

        ChatMessage message = new ChatMessage();
        message.setUser(user);
        message.setTimestamp(Instant.now());
        if(reason == ReasonForLeaving.ERROR)
            message.setType(ChatMessage.Type.ERROR);
        message.setType(ChatMessage.Type.LEFT);
        if(reason == ReasonForLeaving.ERROR)
            message.setContent(user + " left the chat due to an error.");
        else if(reason == ReasonForLeaving.LOGGED_OUT)
            message.setContent(user + " logged out.");
        else
            message.setContent(user + " left the chat.");
        this.logMessage(session, message);

        List<ChatMessage> chatLog = this.chatLogs.remove(id);
        try(FileOutputStream stream = new FileOutputStream("../chat-" + id + ".log"))
        {
            this.objectMapper.writeValue(stream, chatLog);
        }
        catch(IOException e)
        {
            log.error("Error while saving chat log to disk for session {}.", id);
        }

        return message;
    }

    @Override
    public void logMessage(ChatSession session, ChatMessage message)
    {
        List<ChatMessage> chatLog = this.chatLogs.get(session.getSessionId());
        if(chatLog == null)
            log.warn("Attempt made to record chat message in non-existent log.");
        else
            chatLog.add(message);
    }

    @Override
    public List<ChatSession> getPendingSessions()
    {
        return new ArrayList<>(this.pendingSessions.values());
    }

    private synchronized long getNextSessionId()
    {
        return this.SESSION_ID_SEQUENCE++;
    }
}
