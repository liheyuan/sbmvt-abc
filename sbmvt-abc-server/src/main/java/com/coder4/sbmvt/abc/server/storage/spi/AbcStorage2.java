/**
 * @(#)AbcStorage2.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.spi;

import com.coder4.sbmvt.abc.server.data.Abc;

import java.util.List;
import java.util.Optional;

/**
 * @author coder4
 */
public interface AbcStorage2 {

    void saveString(String key, String value);

    String getString(String key);

    List<String> batchGetString(List<String> keys);

    void saveAbc(String key, Abc abc);

    Optional<Abc> getAbc(String key);

    List<Abc> batchGetAbc(List<String> key);


}