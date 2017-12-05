/**
 * @(#)AbcEventReceiver.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.receiver;

import com.coder4.sbmvt.abc.constant.AbcConstant;
import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import com.coder4.sbmvt.rabbitmq.RabbitReceiver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class AbcEventReceiver extends RabbitReceiver<AbcEvent> {

    @Override
    protected void onEvent(AbcEvent abcEvent) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
    protected String getProjectName() {
        return AbcConstant.PROJECT_NAME;
    }

}