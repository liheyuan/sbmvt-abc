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
import java.lang.reflect.ParameterizedType;

/**
 * @author coder4
 */
public abstract class RabbitReceiver<Msg> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected RabbitClient rabbitClient;

    private static int prefetchCount = 1;

    @PostConstruct
    private void init() {
        // TODO
        rabbitClient = new RabbitClient();
        rabbitClient.init();

        try {
            // exchange
            Channel channel = rabbitClient.getChannel();

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

            // consume
            channel.basicConsume(getQueueName(), false, "consumerTag",
                    new DefaultConsumer(channel) {

                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   BasicProperties properties,
                                                   byte[] body) throws IOException {
                            Msg msg = deserilize(body);
                            // Call back
                            onReceive(msg);
                            // Ack after call back
                            long deliveryTag = envelope.getDeliveryTag();
                            channel.basicAck(deliveryTag, false);
                        }
                    });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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