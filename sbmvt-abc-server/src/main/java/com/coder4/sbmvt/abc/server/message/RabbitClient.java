/**
 * @(#)RabbitClient.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    private ConnectionFactory connectionFactory;

    private volatile Connection connection;

    public void init() {
        // Init Connection Factory
        connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setAutomaticRecoveryEnabled(true);

        // Connect
        connect();
    }

    public void stop() {
        try {
            if (connection != null) {
                connection.close();
            }
            LOG.info("RabbitClient stopped");
        } catch (Exception e) {
            LOG.warn("RabbitClient stop excepton", e);
        }
    }

    public Channel createChannel() throws IOException {
        // Make Channel
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        return channel;
    }

    private void connect() {
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeChannel(Channel channel) {
        try {
            channel.close();
        } catch (Exception e) {

        }
    }
}