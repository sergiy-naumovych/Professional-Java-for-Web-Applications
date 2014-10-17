package com.wrox.site.chat;

import javax.websocket.Session;
import java.util.function.Consumer;

public class ChatSession
{
    private long sessionId;
    private String customerUsername;
    private Session customer;
    private String representativeUsername;
    private Session representative;
    private Consumer<Session> onRepresentativeJoin;
    private ChatMessage creationMessage;

    public long getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(long sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getCustomerUsername()
    {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername)
    {
        this.customerUsername = customerUsername;
    }

    public Session getCustomer()
    {
        return customer;
    }

    public void setCustomer(Session customer)
    {
        this.customer = customer;
    }

    public String getRepresentativeUsername()
    {
        return representativeUsername;
    }

    public void setRepresentativeUsername(String representativeUsername)
    {
        this.representativeUsername = representativeUsername;
    }

    public Session getRepresentative()
    {
        return representative;
    }

    public void setRepresentative(Session representative)
    {
        this.representative = representative;
        if(this.onRepresentativeJoin != null)
            this.onRepresentativeJoin.accept(representative);
    }

    public void setOnRepresentativeJoin(Consumer<Session> onRepresentativeJoin)
    {
        this.onRepresentativeJoin = onRepresentativeJoin;
    }

    public ChatMessage getCreationMessage()
    {
        return creationMessage;
    }

    public void setCreationMessage(ChatMessage creationMessage)
    {
        this.creationMessage = creationMessage;
    }
}
