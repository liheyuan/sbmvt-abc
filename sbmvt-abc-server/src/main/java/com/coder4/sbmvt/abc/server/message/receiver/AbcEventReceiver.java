/**
 * @(#)AbcEventReceiver.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.receiver;

import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class AbcEventReceiver extends RabbitReceiver<AbcEvent> implements DisposableBean {

    @Override
    protected void onReceive(AbcEvent abcEvent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (abcEvent.getId() > 2) {
            //throw new RuntimeException("hehe");
        }
        LOG.info("got an message!!!" + Thread.currentThread().getId());
    }

    @Override
    protected String getExchangeName() {
        return AbcEvent.EXCHANGE_NAME;
    }

    @Override
    protected String getQueueName() {
        return "queue1";
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }
}