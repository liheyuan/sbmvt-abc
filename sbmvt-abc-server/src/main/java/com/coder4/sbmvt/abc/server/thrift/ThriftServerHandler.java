/**
 * @(#)RpcHandler.java, Jul 31, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.thrift;

import com.coder4.sbmvt.abc.thrift.SbmvtAbcThrift;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * @author coder4
 */
@Service
public class ThriftServerHandler implements SbmvtAbcThrift.Iface {

    @Override
    public String sayHi() throws TException {
        return "Hi, Spring Boot.";
    }

}
