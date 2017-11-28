/**
 * @(#)ExportAudioJob.java, Jul 14, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.job;

import com.coder4.sbmvt.abc.server.service.impl.DemoServiceImpl;
import com.coder4.sbmvt.abc.server.service.spi.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chentienan
 */
@ConditionalOnProperty(value = "jobs.active", havingValue = "DemoJob")
@Service
@Import(DemoServiceImpl.class)
public class DemoJob implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DemoJob.class);

    @Value("${userId}")
    private int usreId;

    @Value("#{'${ids:}'.split(',')}")
    private List<Integer> ids;

    @Autowired
    private DemoService demoService;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("start DemoJob");

        LOG.info("userId = {}", usreId);

        LOG.info("ids = {}", ids);

        int a = 1, b = 2;

        LOG.info("{} + {} is {}", a, b, demoService.add(a, b));

        LOG.info("end DemoJob");
        System.exit(0);
    }

}