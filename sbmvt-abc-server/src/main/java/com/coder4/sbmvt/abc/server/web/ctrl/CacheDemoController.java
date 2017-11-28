/**
 * @(#)HelloController.java, Aug 28, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.web.ctrl;

import com.coder4.sbmvt.abc.server.cache.CacheDemoKey;
import com.coder4.sbmvt.abc.server.cache.CacheDemoValue;
import com.coder4.sbmvt.cache.LocalCache;
import com.coder4.sbmvt.cache.MemcachedCache;
import com.coder4.sbmvt.cache.configuration.LocalCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coder4
 */
@RequestMapping("sbmvt-abc")
@RestController
public class CacheDemoController {

    @Autowired
    private LocalCache<String, String> localCache;

    @Autowired
    private MemcachedCache<CacheDemoKey, CacheDemoValue> memcachedCache;

    private static String cacheKey = "cache_key";

    @RequestMapping(
            value = "/withLocalCache",
            method = RequestMethod.GET
    )
    public String withCache(@RequestParam String value) {
        String cacheVal = localCache.get(cacheKey);
        return localCache.cacheGet(cacheKey, missKey -> value);
    }

    @RequestMapping(
            value = "/withMemcachedCache",
            method = RequestMethod.GET
    )
    public String withMemcachedCache(@RequestParam String value) {
        CacheDemoKey key = new CacheDemoKey(cacheKey);
        return memcachedCache.cacheGet(key, missKey -> new CacheDemoValue(value)).getValue();
    }

    @RequestMapping(
            value = "/withoutCache",
            method = RequestMethod.GET
    )
    public String withoutCache(@RequestParam String value) {
        return value;
    }

}