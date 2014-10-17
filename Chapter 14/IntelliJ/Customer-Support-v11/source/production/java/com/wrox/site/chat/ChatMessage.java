package com.wrox.site.chat;

import java.time.Instant;

public class ChatMessage
{
    private Instant timestamp;
    private Type type;
    private String user;
    private String content;

    public Instant getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp)
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
