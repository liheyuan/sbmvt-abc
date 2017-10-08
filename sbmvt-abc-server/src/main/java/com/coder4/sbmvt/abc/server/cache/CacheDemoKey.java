/**
 * @(#)CacheDemoValue.java, Oct 08, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.cache;

/**
 * @author coder4
 */
public class CacheDemoKey {
    private String key;

    public CacheDemoKey() {

    }

    public CacheDemoKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}