/**
 * @(#)RabbitClient.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author coder4
 */
public class RabbitClient {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    // TODO
    private String host = "sbmvt-rabbitmq-test";

    // TODO
    private int port = 5672;

    // TODO
    private String username = "sbmvt";

    // TODO
    private String password = "hehehe";

    // TODO
    private String vhost = "sbmvt";

    private int prefetchCount = 1;

    private ConnectionFactory connectionFactory;

    private volatile Connection connection;

    private ThreadLocal<Channel> channelThreadLocal = new ThreadLocal<Channel>();

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void init() {
        // Init Connection Factory
        connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(vhost);

        // Init Connection
        connect();
    }

    public void stop() {
        try {
            connection.close();
            LOG.info("RabbitClient shutdown-ing.");
            executorService.shutdown();
            executorService.awaitTermination(3, TimeUnit.SECONDS);
            LOG.info("RabbitClient shutdown");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            synchronized (this) {
                connection = connectionFactory.newConnection(executorService);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reconnect() {
        if (connection != null && connection.isOpen()) {
            try {
                synchronized (this) {
                    connection.close();
                }
            } catch (Throwable t) {
                LOG.warn("RabbitClient close conn failed", t);
            }
        }
        connect();
        LOG.info("RabbitClient reconnected.");
    }

    private void guaranteeConnect() {
        if (connection == null || !connection.isOpen()) {
            if (connection == null || !connection.isOpen()) {
                reconnect();
            }
        }
    }

    private Channel createChannelTL() throws IOException {
        // Make sure conn is ready
        guaranteeConnect();
        // Make Channel
        Channel channel = connection.createChannel();
        channel.basicQos(prefetchCount);
        // Update thread local
        channelThreadLocal.set(channel);
        return channel;
    }

    public Channel getChannel() {
        try {
            Channel channel = channelThreadLocal.get();

            if (channel == null ||
                    !channel.isOpen() ||
                    channel.getConnection() != connection ||
                    !channel.getConnection().isOpen()) {
                channel = createChannelTL();
            }

            return channel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeChannel() {
        Channel channel = channelThreadLocal.get();
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                LOG.warn("RabbitClient close channel failed.");
            }
        }
    }
}