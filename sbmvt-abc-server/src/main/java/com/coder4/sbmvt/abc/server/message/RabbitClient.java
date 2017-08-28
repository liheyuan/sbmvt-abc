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
import java.util.concurrent.TimeoutException;

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

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void init() {
        // Init Connection Factory
        connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setAutomaticRecoveryEnabled(true);
    }

    public void stop() {
        try {
            LOG.info("RabbitClient shutdown-ing.");
            executorService.shutdown();
            executorService.awaitTermination(3, TimeUnit.SECONDS);
            LOG.info("RabbitClient shutdown");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        try {
            Channel channel = connectionFactory.newConnection(executorService).createChannel();
            channel.basicQos(1);
            return channel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}