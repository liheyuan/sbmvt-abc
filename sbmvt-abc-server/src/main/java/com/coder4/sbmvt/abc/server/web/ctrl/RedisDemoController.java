/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.constant.AbcConstant;
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
@RequestMapping(AbcConstant.REST_API)
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
        abc.setAbc(RandomStringUtils.randomAlphanumeric(6));
        abcStorage2.saveAbc(id, abc);
        //System.out.println(abcStorage2.getAbc("ddd").orElse(null));

        //abcStorage2.getString("1");

        //abcStorage2.saveString(id, RandomStringUtils.randomAlphanumeric(10));
        return "hello, " + id + "," + abcStorage2.getAbc(id);


        /*
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 500000; i++) {
            //abcStorage2.saveString(Integer.toString(i), RandomStringUtils.randomAlphanumeric(6));
            keys.add(Integer.toString(i));
        }

        long start = System.currentTimeMillis();
        abcStorage2.batchGetString(keys);
        //keys.stream().forEach(key -> abcStorage2.getString(key));
        System.out.println(System.currentTimeMillis() - start);
        */

        /*
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            //abcStorage2.saveAbc(Integer.toString(i), new Abc());
            keys.add(Integer.toString(i));
        }

        long start = System.currentTimeMillis();
        abcStorage2.batchGetAbc(keys);
        //keys.stream().forEach(key -> abcStorage2.getString(key));
        System.out.println(System.currentTimeMillis() - start);

        return "Hello, pipelined";
        */

    }

}