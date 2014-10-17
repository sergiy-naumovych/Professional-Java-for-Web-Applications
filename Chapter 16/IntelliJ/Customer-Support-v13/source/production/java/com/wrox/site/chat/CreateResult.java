package com.wrox.site.chat;

public class CreateResult
{
    private final ChatSession chatSession;
    private final ChatMessage createMessage;

    public CreateResult(ChatSession chatSession, ChatMessage createMessage)
    {
        this.chatSession = chatSession;
        this.createMessage = createMessage;
    }

    public ChatSession getChatSession()
    {
        return chatSession;
    }

    public ChatMessage getCreateMessage()
    {
        return createMessage;
    }
}
