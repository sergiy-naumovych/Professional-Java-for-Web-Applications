package com.wrox.site;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class ClusterEventMulticaster extends SimpleApplicationEventMulticaster
{
    private static final Logger log = LogManager.getLogger();
    private static final String EXCHANGE_NAME = "AMQPMessagingTest";
    private static final String HEADER = "X-Wrox-Cluster-Node";

    private final AMQP.BasicProperties.Builder builder =
            new AMQP.BasicProperties.Builder();
    private Connection amqpConnection;
    private Channel amqpChannel;
    private String queueName;
    private Thread listener;
    private boolean destroyed = false;

    @Override
    public final void multicastEvent(ApplicationEvent event)
    {
        try
        {
            super.multicastEvent(event);
        }
        finally
        {
            try
            {
                if(event instanceof ClusterEvent &&
                        !((ClusterEvent)event).isRebroadcasted())
                    this.publishClusteredEvent((ClusterEvent)event);
            }
            catch(Exception e)
            {
                log.error("Failed to broadcast distributable event to cluster.",
                        e);
            }
        }
    }

    protected void publishClusteredEvent(ClusterEvent event) throws IOException
    {
        this.amqpChannel.basicPublish(EXCHANGE_NAME, "", this.builder.build(),
                SerializationUtils.serialize(event));
    }

    @PostConstruct
    public void setupRabbitConnection() throws IOException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.amqpConnection = factory.newConnection();
        this.amqpChannel = this.amqpConnection.createChannel();

        this.amqpChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        this.queueName = this.amqpChannel.queueDeclare().getQueue();
        this.amqpChannel.queueBind(this.queueName, EXCHANGE_NAME, "");

        Map<String, Object> headers = new Hashtable<>();
        headers.put(HEADER, this.queueName);
        this.builder.headers(headers);

        this.listener = new Thread(this::listen, "RabbitMQ-1");
        this.listener.start();
    }

    @PreDestroy
    public void shutdownRabbitConnection() throws IOException
    {
        this.destroyed = true;
        this.listener.interrupt();
        this.amqpChannel.close();
        this.amqpConnection.close();
    }

    private void listen()
    {
        try
        {
            QueueingConsumer consumer = new QueueingConsumer(this.amqpChannel);
            this.amqpChannel.basicConsume(this.queueName, true, consumer);

            while(!this.destroyed)
            {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Object header = delivery.getProperties().getHeaders().get(HEADER);
                if(header == null || !header.toString().equals(this.queueName))
                {
                    ClusterEvent event = (ClusterEvent)SerializationUtils
                            .deserialize(delivery.getBody());
                    event.setRebroadcasted();
                    this.multicastEvent(event);
                }
            }
        }
        catch(Exception e)
        {
            if(!this.destroyed)
                log.error("Error while listening for message deliveries.", e);
        }
    }
}
