/**
 * @(#)DemoServiceImpl.java, Nov 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.service.impl;

import com.coder4.sbmvt.abc.server.service.spi.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}