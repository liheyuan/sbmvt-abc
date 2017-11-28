/**
 * @(#)ExportAudioJob.java, Jul 14, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chentienan
 */
@ConditionalOnProperty(value = "jobs.active", havingValue = "DemoJob")
@Service
public class DemoJob implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DemoJob.class);

    @Value("${userId}")
    private int usreId;

    @Value("#{'${ids:}'.split(',')}")
    private List<Integer> ids;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("start DemoJob");

        LOG.info("userId = {}", usreId);

        LOG.info("ids = {}", ids);

        LOG.info("end DemoJob");
    }

}