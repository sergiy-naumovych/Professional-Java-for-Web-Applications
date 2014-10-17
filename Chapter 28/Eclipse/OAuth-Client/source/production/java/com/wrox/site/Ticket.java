package com.wrox.site;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Ticket
{
    private long id;

    private UserPrincipal customer;

    private String subject;

    private String body;

    private Instant dateCreated;

    private List<Attachment> attachments = new ArrayList<>();

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public UserPrincipal getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(UserPrincipal customer)
    {
        this.customer = customer;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Instant getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public List<Attachment> getAttachments()
    {
        return this.attachments;
    }

    public void setAttachments(List<Attachment> attachments)
    {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment)
    {
        this.attachments.add(attachment);
    }

    @JsonIgnore
    public int getNumberOfAttachments()
    {
        return this.attachments.size();
    }
}
