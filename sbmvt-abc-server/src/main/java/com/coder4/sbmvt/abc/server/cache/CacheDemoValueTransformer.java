/**
 * @(#)CacheDemoValueTransformer.java, Oct 08, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.cache;

import com.coder4.sbmvt.cache.JsonCacheValueTransformer;

/**
 * @author coder4
 */
public class CacheDemoValueTransformer extends JsonCacheValueTransformer<CacheDemoValue> {

    public CacheDemoValueTransformer() {
        super(CacheDemoValue.class);
    }
}