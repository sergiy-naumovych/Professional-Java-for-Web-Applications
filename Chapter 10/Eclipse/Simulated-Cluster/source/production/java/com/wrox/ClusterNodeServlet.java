package com.wrox;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ClusterNodeServlet extends HttpServlet
{
    private Session session;

    private String nodeId;

    @Override
    public void init() throws ServletException
    {
        this.nodeId = this.getInitParameter("nodeId");
        String path = this.getServletContext().getContextPath() +
                "/clusterNodeSocket/" + this.nodeId;
        try
        {
            URI uri = new URI("ws", "localhost:8080", path, null, null);
            this.session = ContainerProvider.getWebSocketContainer()
                    .connectToServer(this, uri);
        }
        catch(URISyntaxException | IOException | DeploymentException e)
        {
            throw new ServletException("Cannot connect to " + path + ".", e);
        }
    }

    @Override
    public void destroy()
    {
        try
        {
            this.session.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ClusterMessage message = new ClusterMessage(this.nodeId,
                "request:{ip:\"" + request.getRemoteAddr() +
                "\",queryString:\"" + request.getQueryString() + "\"}");

        try(OutputStream output = this.session.getBasicRemote().getSendStream();
            ObjectOutputStream stream = new ObjectOutputStream(output))
        {
            stream.writeObject(message);
        }
        response.getWriter().append("OK");
    }

    @OnMessage
    public void onMessage(InputStream input)
    {
        try(ObjectInputStream stream = new ObjectInputStream(input))
        {
            ClusterMessage message = (ClusterMessage)stream.readObject();
            System.out.println("INFO (Node " + this.nodeId +
                    "): Message received from cluster; node = " +
                    message.getNodeId() + ", message = " + message.getMessage());
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(CloseReason reason)
    {
        CloseReason.CloseCode code = reason.getCloseCode();
        if(code != CloseReason.CloseCodes.NORMAL_CLOSURE)
        {
            System.err.println("ERROR: WebSocket connection closed unexpectedly;" +
                    " code = " + code + ", reason = " + reason.getReasonPhrase());
        }
    }
}
