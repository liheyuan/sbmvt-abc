/**
 * @(#)AbcStorage.java, Dec 12, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.spi;

import com.coder4.sbmvt.abc.server.data.Abc;

import java.util.Optional;

/**
 * @author coder4
 */
public interface AbcStorage {

    int saveAbc(Abc abc);

    Optional<Abc> getAbc(int id);

}