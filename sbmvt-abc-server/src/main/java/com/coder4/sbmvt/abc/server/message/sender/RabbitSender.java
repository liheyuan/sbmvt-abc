/**
 * @(#)BaseSender.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.sender;

import com.coder4.sbmvt.abc.server.message.RabbitClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author coder4
 */
public abstract class RabbitSender<T> {

    private static final int SENDER_RETRY_QUEUE_MAX_SIZE = 1024 * 1024 * 1024;

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private ObjectMapper jsonObjectMapper;

    protected RabbitClient rabbitClient;

    protected LinkedBlockingDeque<T> senderRetryQueue;

    private Thread retryThread;

    private Channel channel;

    @PostConstruct
    public void init() throws Exception {
        // TODO
        rabbitClient = new RabbitClient();
        rabbitClient.init();

        jsonObjectMapper = Jackson2ObjectMapperBuilder.json().build();

        senderRetryQueue = new LinkedBlockingDeque<>(SENDER_RETRY_QUEUE_MAX_SIZE);

        channel = rabbitClient.createChannel();

        start();
    }

    public void stop() {
        retryThread.interrupt();
        rabbitClient.stop();
    }

    public void start() {
        retryThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    send(senderRetryQueue.take());
                } catch (InterruptedException e) {
                    // will exit while next tick
                } catch (Throwable t) {
                    LOG.error("RabbitSender retry thread exception", t);
                }
            }
        });

        retryThread.start();
    }

    public void send(T msg) {
        byte[] payload = serialize(msg);
        try {
            channel.basicPublish(
                    getExchangeName(),
                    getRoutingKey(msg),
                    false,
                    false,
                    MessageProperties.MINIMAL_PERSISTENT_BASIC,
                    payload);

            LOG.debug("RabbitSender success send a msg.");
        } catch (Throwable t) {
            LOG.error("RabbitSender exception", t);
            senderRetryQueue.offer(msg);
        }
    }

    protected byte[] serialize(T msg) {
        try {
            return jsonObjectMapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getExchangeName();

    protected String getRoutingKey(T msg) {
        // Default
        return "#";
    }

}