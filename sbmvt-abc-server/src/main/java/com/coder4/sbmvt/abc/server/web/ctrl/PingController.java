/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.constant.AbcConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coder4
 */
@RequestMapping(value = AbcConstant.REST_API + "/ping")
@RestController
public class PingController {

    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

    // http://localhost:8080/sbmvt-abc/api/ping?msg=a
    @RequestMapping(
            method = RequestMethod.GET
    )
    public String echoWithMessage(@RequestParam String msg) {
        return msg;
    }

}