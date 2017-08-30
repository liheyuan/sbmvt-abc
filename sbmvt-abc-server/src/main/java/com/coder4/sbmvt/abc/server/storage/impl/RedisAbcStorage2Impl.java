/**
 * @(#)RedisAbcStorage2Impl.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.impl;

import com.coder4.sbmvt.abc.server.data.Abc;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author coder4
 */
@Repository
public class RedisAbcStorage2Impl implements AbcStorage2 {

    private static final String REDIS_STRING_PREFIX = "str_";

    private static final String REDIS_ABC_PREFIX = "abc_";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Abc> abcRedisTemplate;

    private String keyForString(String key) {
        return REDIS_STRING_PREFIX + key;
    }

    @Override
    public void saveString(String key, String value) {
        stringRedisTemplate.opsForValue().set(keyForString(key), value);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(keyForString(key));
    }

    private String keyForAbc(String key) {
        return REDIS_ABC_PREFIX + key;
    }

    @Override
    public void saveAbc(String key, Abc abc) {
        abcRedisTemplate.opsForValue().set(keyForAbc(key), abc);
    }

    @Override
    public Abc readAbc(String key) {
        return abcRedisTemplate.opsForValue().get(keyForAbc(key));
    }
}