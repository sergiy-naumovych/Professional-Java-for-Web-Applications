package com.wrox.site.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wrox.site.converters.InstantConverter;
import com.wrox.site.validation.NotBlank;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TicketComment")
@XmlRootElement(namespace = "http://example.com/xmlns/support", name = "comment")
@XmlAccessorType(XmlAccessType.NONE)
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class TicketComment implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long id;

    private long ticketId;

    @NotNull(message = "{validate.ticket.comment.customer}")
    private UserPrincipal customer;

    @NotBlank(message = "{validate.ticket.comment.body}")
    private String body;

    private Instant dateCreated;

    @Valid
    private List<Attachment> attachments = new ArrayList<>();

    @Id
    @Column(name = "CommentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    @JsonProperty
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Basic
    @XmlElement
    @JsonProperty
    public long getTicketId()
    {
        return this.ticketId;
    }

    public void setTicketId(long ticketId)
    {
        this.ticketId = ticketId;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "UserId")
    @XmlElement
    @JsonProperty
    public UserPrincipal getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(UserPrincipal customer)
    {
        this.customer = customer;
    }

    @Lob
    @XmlElement
    @JsonProperty
    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    @Convert(converter = InstantConverter.class)
    @XmlSchemaType(name = "dateTime")
    @XmlElement
    @JsonProperty
    public Instant getDateCreated()
    {
        return this.dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name = "TicketComment_Attachment",
            joinColumns = { @JoinColumn(name = "CommentId") },
            inverseJoinColumns = { @JoinColumn(name = "AttachmentId") })
    @OrderColumn(name = "SortKey")
    @XmlElement(name = "attachment")
    @JsonProperty
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

    @Transient
    public int getNumberOfAttachments()
    {
        return this.attachments.size();
    }
}
