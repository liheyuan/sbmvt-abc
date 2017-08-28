/**
 * @(#)MySampleClientTest.java, Aug 01, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.client;

import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift;
import com.coder4.sbmvt.thrift.client.ThriftClient;

import java.util.concurrent.Future;

/**
 * @author coder4
 */
public class SbmvtAbcEasyClientTest {

    public static void test1() {
        ThriftClient<SbmvtAbcThrift.Client> client = SbmvtAbcEasyClientBuilder
                .buildClient("127.0.0.1", 3000);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i<10000; i++) {
            String ret = client.call(cli -> cli.sayHi());
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        //System.out.println(ret);
    }

    public static void test2() throws Exception {
        ThriftClient<SbmvtAbcThrift.Client> client = SbmvtAbcEasyClientBuilder
                .buildClient("127.0.0.1", 3000);

        Future<String> fRet = client.asyncCall(cli -> cli.sayHi());
        System.out.println(fRet.get());
    }

    public static void main(String [] args) throws Exception {
        test1();
        //test2();
    }

}
