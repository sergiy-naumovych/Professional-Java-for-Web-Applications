package com.wrox.site.entity;

import java.time.Instant;

public class Reply
{
    private long id;
    private long discussionId;
    private String user;
    private String message;
    private Instant created;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getDiscussionId()
    {
        return discussionId;
    }

    public void setDiscussionId(long discussionId)
    {
        this.discussionId = discussionId;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Instant getCreated()
    {
        return created;
    }

    public void setCreated(Instant created)
    {
        this.created = created;
    }
}
