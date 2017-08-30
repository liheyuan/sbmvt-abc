/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.server.data.Abc;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage2;
import org.apache.commons.lang.RandomStringUtils;
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
public class RedisDemoController {

    @Autowired
    private AbcStorage2 abcStorage2;

    @RequestMapping(
            value = "/redis/{id}",
            method = RequestMethod.GET
    )
    public String getLessonUserReport(
            @PathVariable(value = "id") String id) {
        Abc abc = new Abc();
        abc.setId(1);
        abc.setSbc(RandomStringUtils.randomAlphanumeric(6));
        abcStorage2.saveAbc(id, abc);
        abcStorage2.getString("dd");

        abcStorage2.saveString(id, RandomStringUtils.randomAlphanumeric(10));
        return "hello, " + id + "," + abcStorage2.getString(id);
    }

}