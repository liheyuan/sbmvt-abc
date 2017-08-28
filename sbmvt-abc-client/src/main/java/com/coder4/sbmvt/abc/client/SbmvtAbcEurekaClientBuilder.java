/**
 * @(#)MySampleClientBuilder.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.client;

import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift;
import com.coder4.sbmvt.thrift.client.builder.EurekaThriftClientBuilder;
import com.coder4.sbmvt.thrift.client.ThriftClient;

/**
 * @author coder4
 */
public class SbmvtAbcEurekaClientBuilder extends EurekaThriftClientBuilder<SbmvtAbcThrift.Client> {

    public SbmvtAbcEurekaClientBuilder(String serviceName) {
        setThriftClass(SbmvtAbcThrift.class);

        setThriftServiceName(serviceName);
    }

    public static ThriftClient<SbmvtAbcThrift.Client> buildClient(String serviceName) {
        return new SbmvtAbcEurekaClientBuilder(serviceName).build();
    }

}
