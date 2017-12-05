/**
 * @(#)DemoServiceImplTest.java, Nov 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.service.impl;

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