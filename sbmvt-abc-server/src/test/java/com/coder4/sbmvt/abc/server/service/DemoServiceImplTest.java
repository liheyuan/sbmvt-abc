/**
 * @(#)DemoServiceImplTest.java, Dec 07, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.service;

import com.coder4.sbmvt.abc.server.service.impl.DemoServiceImpl;
import com.coder4.sbmvt.abc.server.service.spi.DemoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * @author coder4
 */
public class DemoServiceImplTest {

    private DemoService demoService;

    @Before
    public void before() {
        demoService = new DemoServiceImpl();
    }

    @Test
    public void test() {
        int a = 1;
        int b = 2;

        Assert.assertThat(a + b, is(demoService.add(a, b)));
    }


}