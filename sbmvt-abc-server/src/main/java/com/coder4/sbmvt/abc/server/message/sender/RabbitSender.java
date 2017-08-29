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
import java.io.IOException;
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

    private Channel senderChannel;

    private Channel retryChannel;

    @PostConstruct
    public void init() throws Exception {
        // TODO
        rabbitClient = new RabbitClient();
        rabbitClient.init();

        jsonObjectMapper = Jackson2ObjectMapperBuilder.json().build();

        senderRetryQueue = new LinkedBlockingDeque<>(SENDER_RETRY_QUEUE_MAX_SIZE);

        senderChannel = rabbitClient.createChannel();

        start();
    }

    public void stop() {
        try {
            senderChannel.close();
            retryChannel.close();
            retryThread.interrupt();
            retryThread.join();
        } catch (Exception e) {
            LOG.warn("join retryThread failed", e);
        }
        rabbitClient.stop();
    }

    public void start() {
        retryThread = new Thread(() -> {
            try {
                retryChannel = rabbitClient.createChannel();
            } catch (IOException e) {
                LOG.error("Create retryThread failed.", e);
                return;
            }

            while (!Thread.currentThread().isInterrupted()) {
                T msg = null;
                try {
                    msg = senderRetryQueue.take();
                    resend(msg);
                    LOG.info("resend success");
                } catch (InterruptedException e) {
                    // will exit while next tick
                } catch (Throwable t) {
                    LOG.error("RabbitSender retry thread exception", t);
                    if (msg != null) {
                        senderRetryQueue.offer(msg);
                    }
                }
            }
        });

        retryThread.start();
    }

    public void send(T msg) {
        try {
            doSend(senderChannel, msg);
        } catch (Exception e) {
            LOG.error("RabbitSender exception", e);
            senderRetryQueue.offer(msg);
        }
    }

    private void resend(T msg) throws Exception {
        doSend(retryChannel, msg);
    }

    private void doSend(Channel channel, T msg) throws Exception {
        byte[] payload = serialize(msg);
        channel.basicPublish(
                getExchangeName(),
                getRoutingKey(msg),
                false,
                false,
                MessageProperties.MINIMAL_PERSISTENT_BASIC,
                payload);

        LOG.debug("RabbitSender success send a msg.");
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