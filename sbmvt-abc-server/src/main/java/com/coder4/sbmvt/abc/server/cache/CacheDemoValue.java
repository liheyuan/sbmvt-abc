/**
 * @(#)CacheDemoValue.java, Oct 08, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.cache;

/**
 * @author coder4
 */
public class CacheDemoValue {
    private String value;

    public CacheDemoValue() {

    }

    public CacheDemoValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}