/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.constant.AbcConstant;
import com.coder4.sbmvt.abc.server.data.Abc;
import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import com.coder4.sbmvt.abc.server.message.receiver.AbcEventReceiver;
import com.coder4.sbmvt.abc.server.message.sender.AbcEventSender;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coder4
 */
@RequestMapping(AbcConstant.REST_API + "/database")
@RestController
public class DatabaseDemoController {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseDemoController.class);

    @Autowired
    private AbcStorage abcStorage;

    @RequestMapping(
            value = "/abc",
            method = RequestMethod.POST
    )
    public int save(
            @RequestBody Abc abc) {
        return abcStorage.saveAbc(abc);
    }

    @RequestMapping(
            value = "/abc/{id}",
            method = RequestMethod.GET
    )
    public Abc get(
            @PathVariable int id) {
        return abcStorage.getAbc(id).orElseThrow(() -> new RuntimeException("hehe"));
    }

}