/**
 * @(#)CacheConfiguration.java, Oct 08, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.configuration;

import com.coder4.sbmvt.abc.server.cache.CacheDemoKey;
import com.coder4.sbmvt.abc.server.cache.CacheDemoValue;
import com.coder4.sbmvt.abc.server.cache.CacheDemoValueTransformer;
import com.coder4.sbmvt.cache.DefaultCacheKeyTransformer;
import com.coder4.sbmvt.cache.LocalCache;
import com.coder4.sbmvt.cache.MemcachedCache;
import com.coder4.sbmvt.cache.builder.LocalCacheBuilder;
import com.coder4.sbmvt.cache.builder.MemcachedCacheBuilder;
import com.coder4.sbmvt.cache.configuration.LocalCacheConfig;
import com.coder4.sbmvt.cache.configuration.MemcachedCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coder4
 */
@Configuration
public class CacheConfiguration {

    private static final String cacheType = "cacheTypeTest";

    @Bean
    @ConfigurationProperties(prefix = "sbmvtAbc.localCache")
    LocalCacheConfig createLocalCacheConfig() {
        return new LocalCacheConfig();
    }

    @Bean
    LocalCache<String, String> createLocalCache(@Autowired LocalCacheConfig config) {
        return new LocalCacheBuilder().createCache(config);
    }

    @Bean
    @ConfigurationProperties(prefix = "sbmvtAbc.memcachedCache")
    MemcachedCacheConfig createMemcachedCacheConfig() {
        return new MemcachedCacheConfig();
    }

    @Bean
    MemcachedCache<CacheDemoKey, CacheDemoValue> createMemcachedCache(@Autowired MemcachedCacheConfig config)
            throws Exception {
        return new MemcachedCacheBuilder().createCache(config,
                new DefaultCacheKeyTransformer(cacheType),
                new CacheDemoValueTransformer());
    }

}