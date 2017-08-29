/**
 * @(#)AbcEventSender.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.sender;

import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import com.coder4.sbmvt.rabbitmq.RabbitSender;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class AbcEventSender extends RabbitSender<AbcEvent> implements DisposableBean {

    @Override
    protected String getExchangeName() {
        return AbcEvent.EXCHANGE_NAME;
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }
}