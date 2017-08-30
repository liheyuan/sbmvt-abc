/**
 * @(#)AbcStorage2.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.spi;

import com.coder4.sbmvt.abc.server.data.Abc;

/**
 * @author coder4
 */
public interface AbcStorage2 {

    void saveString(String key, String value);

    String getString(String key);

    void saveAbc(String key, Abc abc);

    Abc readAbc(String key);


}