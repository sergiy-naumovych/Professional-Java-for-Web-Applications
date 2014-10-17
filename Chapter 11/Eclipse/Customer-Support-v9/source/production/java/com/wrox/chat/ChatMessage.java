package com.wrox.chat;

import java.time.OffsetDateTime;

public class ChatMessage
{
    private OffsetDateTime timestamp;
    private Type type;
    private String user;
    private String content;

    public OffsetDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public static enum Type
    {
        STARTED, JOINED, ERROR, LEFT, TEXT
    }
}
