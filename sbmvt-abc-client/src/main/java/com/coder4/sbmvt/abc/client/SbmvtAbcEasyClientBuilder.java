/**
 * @(#)MySampleClientBuilder.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.client;

import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift;
import com.coder4.sbmvt.thrift.client.builder.EasyThriftClientBuilder;
import com.coder4.sbmvt.thrift.client.ThriftClient;

/**
 * @author coder4
 */
public class SbmvtAbcEasyClientBuilder extends EasyThriftClientBuilder<SbmvtAbcThrift.Client> {

    public SbmvtAbcEasyClientBuilder(String host, int port) {
        setThriftClass(SbmvtAbcThrift.class);

        setHost(host);
        setPort(port);
    }

    public static ThriftClient<SbmvtAbcThrift.Client> buildClient(String host, int port) {
        return new SbmvtAbcEasyClientBuilder(host, port).build();
    }

}
