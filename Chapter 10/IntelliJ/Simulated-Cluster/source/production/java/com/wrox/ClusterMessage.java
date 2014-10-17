package com.wrox;

import java.io.Serializable;

public class ClusterMessage implements Serializable
{
    private String nodeId;

    private String message;

    public ClusterMessage()
    {

    }

    public ClusterMessage(String nodeId, String message)
    {
        this.nodeId = nodeId;
        this.message = message;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
