package com.wrox.site.chat;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ChatService
{
    @PreAuthorize("authentication.principal.username.equals(#user) and " +
            "hasAuthority('CREATE_CHAT_REQUEST')")
    CreateResult createSession(@P("user") String user);
    @PreAuthorize("authentication.principal.username.equals(#user) and " +
            "hasAuthority('START_CHAT')")
    JoinResult joinSession(long id, @P("user") String user);
    @PreAuthorize("authentication.principal.username.equals(#user) and " +
            "(#user.equals(#session.customerUsername) or " +
            "#user.equals(#session.representativeUsername)) and " +
            "hasAuthority('CHAT')")
    ChatMessage leaveSession(@P("session") ChatSession session,
                             @P("user") String user, ReasonForLeaving reason);
    @PreAuthorize("authentication.principal.username.equals(#message.user) and " +
            "(#message.user.equals(#session.customerUsername) or " +
            "#message.user.equals(#session.representativeUsername)) and " +
            "hasAuthority('CHAT')")
    void logMessage(@P("session") ChatSession session,
                    @P("message") ChatMessage message);
    @PreAuthorize("hasAuthority('VIEW_CHAT_REQUESTS')")
    List<ChatSession> getPendingSessions();

    public static enum ReasonForLeaving
    {
        NORMAL,

        LOGGED_OUT,

        ERROR
    }
}
