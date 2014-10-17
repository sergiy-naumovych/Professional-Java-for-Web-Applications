package com.wrox.site.chat;

import java.util.List;

public interface ChatService
{
    CreateResult createSession(String user);
    JoinResult joinSession(long id, String user);
    ChatMessage leaveSession(ChatSession session, String user,
                             ReasonForLeaving reason);
    void logMessage(ChatSession session, ChatMessage message);
    List<ChatSession> getPendingSessions();

    public static enum ReasonForLeaving
    {
        NORMAL,

        LOGGED_OUT,

        ERROR
    }
}
