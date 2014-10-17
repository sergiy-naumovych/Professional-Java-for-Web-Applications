package com.wrox.site.chat;

public class JoinResult
{
    private final ChatSession chatSession;
    private final ChatMessage joinMessage;

    public JoinResult(ChatSession chatSession, ChatMessage joinMessage)
    {
        this.chatSession = chatSession;
        this.joinMessage = joinMessage;
    }

    public ChatSession getChatSession()
    {
        return chatSession;
    }

    public ChatMessage getJoinMessage()
    {
        return joinMessage;
    }
}
