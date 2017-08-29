/**
 * @(#)BaseReceiver.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.receiver;

import com.coder4.sbmvt.abc.server.message.RabbitClient;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author coder4
 */
public abstract class RabbitReceiver<Msg> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected RabbitClient rabbitClient;

    List<WeakReference<Channel>> channels = new ArrayList();

    private static int consumerCount = 20;

    @PostConstruct
    private void init() {
        // TODO
        rabbitClient = new RabbitClient();
        rabbitClient.init();

        // thread pool
        for (int i = 0; i < consumerCount; i++) {
            try {
                Channel channel = newChannel();
                registerConsume(channel);
                channels.add(new WeakReference<>(channel));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private Channel newChannel() throws IOException {

        try {

            // exchange
            Channel channel = rabbitClient.createChannel();

            channel.exchangeDeclare(getExchangeName(),
                    "topic",
                    true,
                    false,
                    false,
                    null);

            // queue
            channel.queueDeclare(getQueueName(),
                    true,
                    false,
                    false,
                    null);

            // bind exchange & queue
            String routingKey = getRoutingKey();
            channel.queueBind(getQueueName(), getExchangeName(), routingKey, null);

            LOG.info("RabbitReceiver bind queue {} to exchange {} with routingKey {}",
                    getQueueName(),
                    getExchangeName(),
                    routingKey);
            return channel;
        } catch (Exception e) {
            LOG.error("RabbitReceiver newChannel exception", e);
            return null;
        }
    }

    private void registerConsume(Channel channel) throws Exception {

        // consume
        channel.basicConsume(getQueueName(), false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       BasicProperties properties,
                                       byte[] body) throws IOException {
                Msg msg = deserilize(body);
                // Call back
                try {
                    onReceive(msg);
                } catch (Exception e) {
                    LOG.error("RabbitReceiver exception", e);
                }
                // Ack after call back
                long deliveryTag = envelope.getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        });
    }

    protected Msg deserilize(byte[] data) {
        Class<Msg> t = (Class<Msg>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return Jackson2ObjectMapperBuilder.json().build().readValue(data, t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void stop() {
        channels.stream().forEach(wr -> {
            Channel channel = wr.get();
            if (channel != null) {
                RabbitClient.closeChannel(channel);
            }
        });
        rabbitClient.stop();
    }

    protected abstract void onReceive(Msg msg);

    protected abstract String getExchangeName();

    protected abstract String getQueueName();

    protected String getRoutingKey() {
        // Default # as routing key
        return "#";
    }
}