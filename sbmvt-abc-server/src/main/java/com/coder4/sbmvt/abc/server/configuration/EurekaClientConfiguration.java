/**
 * @(#)EurekaClientConfiguration.java, Aug 31, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.configuration;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author coder4
 */
@Configuration
@EnableEurekaClient()
public class EurekaClientConfiguration {

}