/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.constant.AbcConstant;
import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import com.coder4.sbmvt.abc.server.message.receiver.AbcEventReceiver;
import com.coder4.sbmvt.abc.server.message.sender.AbcEventSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coder4
 */
@RequestMapping(AbcConstant.REST_API + "/rabbit")
@RestController
public class RabbitController {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitController.class);

    @Autowired
    private AbcEventSender sender;

    @Autowired
    private AbcEventReceiver receiver;

    @RequestMapping(
            value = "/sendEvent/{cnt}",
            method = RequestMethod.GET
    )
    public String sendEvent(
            @PathVariable(value = "cnt") int cnt) {
        for (int i = 0; i < cnt; i++) {
            AbcEvent event = new AbcEvent();
            event.setId(i);
            sender.send(event);
        }
        return cnt + " events send.";
    }

}