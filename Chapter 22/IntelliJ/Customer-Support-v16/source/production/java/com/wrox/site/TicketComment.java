package com.wrox.site;

import com.wrox.site.validation.NotBlank;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import java.time.Instant;

@XmlRootElement(namespace = "http://example.com/xmlns/support", name = "comment")
public class TicketComment
{
    private long id;

    @NotBlank(message = "{validate.ticket.comment.customerName}")
    private String customerName;

    @NotBlank(message = "{validate.ticket.comment.body}")
    private String body;

    private Instant dateCreated;

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCustomerName()
    {
        return this.customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    @XmlSchemaType(name = "dateTime")
    public Instant getDateCreated()
    {
        return this.dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }
}
