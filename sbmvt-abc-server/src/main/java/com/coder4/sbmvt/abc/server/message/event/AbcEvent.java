/**
 * @(#)AbcEvent.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.message.event;

/**
 * @author coder4
 */
public class AbcEvent {

    public static final String EXCHANGE_NAME = "sbmvt-abc#abc";

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}