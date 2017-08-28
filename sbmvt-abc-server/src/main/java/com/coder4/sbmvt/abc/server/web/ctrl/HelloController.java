/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.server.message.event.AbcEvent;
import com.coder4.sbmvt.abc.server.message.sender.AbcEventSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coder4
 */
@RequestMapping("sbmvt-abc")
@RestController
public class HelloController {

    @Autowired
    private AbcEventSender sender;

    @RequestMapping(
            value = "/hello/{id}",
            method = RequestMethod.GET
    )
    public String getLessonUserReport(
            @PathVariable(value = "id") String id) {
        sender.send(new AbcEvent());
        return "hello, " + id;
    }

}