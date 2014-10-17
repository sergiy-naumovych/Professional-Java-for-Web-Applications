package com.wrox.site.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class ChatMessage implements Cloneable
{
    private Instant timestamp;
    private Type type;
    private String user;
    private String contentCode;
    private Object[] contentArguments;
    private String localizedContent;
    private String userContent;

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

    public String getContentCode()
    {
        return contentCode;
    }

    public void setContentCode(String contentCode)
    {
        this.contentCode = contentCode;
    }

    public Object[] getContentArguments()
    {
        return contentArguments;
    }

    public void setContentArguments(Object... contentArguments)
    {
        this.contentArguments = contentArguments;
    }

    public String getLocalizedContent()
    {
        return localizedContent;
    }

    public void setLocalizedContent(String localizedContent)
    {
        this.localizedContent = localizedContent;
    }

    public String getUserContent()
    {
        return userContent;
    }

    public void setUserContent(String userContent)
    {
        this.userContent = userContent;
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected ChatMessage clone() {
        try {
            return (ChatMessage)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Impossible clone not supported.", e);
        }
    }

    public static enum Type
    {
        STARTED, JOINED, ERROR, LEFT, TEXT
    }

    static abstract class MixInForLogWrite
    {
        @JsonIgnore public abstract String getLocalizedContent();
        @JsonIgnore public abstract void setLocalizedContent(String l);
    }

    static abstract class MixInForWebSocket
    {
        @JsonIgnore public abstract String getContentCode();
        @JsonIgnore public abstract void setContentCode(String c);
        @JsonIgnore public abstract Object[] getContentArguments();
        @JsonIgnore public abstract void setContentArguments(Object... c);
    }
}
