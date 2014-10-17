package com.wrox;

import java.io.Serializable;
import java.net.InetAddress;

public class PageVisit implements Serializable
{
    private long enteredTimestamp;

    private Long leftTimestamp;

    private String request;

    private InetAddress ipAddress;

    public long getEnteredTimestamp()
    {
        return enteredTimestamp;
    }

    public void setEnteredTimestamp(long enteredTimestamp)
    {
        this.enteredTimestamp = enteredTimestamp;
    }

    public Long getLeftTimestamp()
    {
        return leftTimestamp;
    }

    public void setLeftTimestamp(Long leftTimestamp)
    {
        this.leftTimestamp = leftTimestamp;
    }

    public String getRequest()
    {
        return request;
    }

    public void setRequest(String request)
    {
        this.request = request;
    }

    public InetAddress getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress)
    {
        this.ipAddress = ipAddress;
    }
}
