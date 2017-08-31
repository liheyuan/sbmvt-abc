/**
 * @(#)RedisAbcStorage2Impl.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.storage.impl;

import com.coder4.sbmvt.RedisJsonSerializer;
import com.coder4.sbmvt.abc.server.data.Abc;
import com.coder4.sbmvt.abc.server.storage.spi.AbcStorage2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<String> batchGetString(List<String> keys) {

        RedisCallback<List<Object>> callback = connection -> {
            connection.openPipeline();

            keys.stream().forEach(key -> connection.get(keyForString(key).getBytes()));

            return connection.closePipeline();
        };

        return stringRedisTemplate.execute(callback).stream()
                .map(obj -> obj == null ? null : obj.toString())
                .collect(Collectors.toList());
    }

    private String keyForAbc(String key) {
        return REDIS_ABC_PREFIX + key;
    }

    @Override
    public void saveAbc(String key, Abc abc) {
        abcRedisTemplate.opsForValue().set(keyForAbc(key), abc);
    }

    @Override
    public Optional<Abc> getAbc(String key) {
        return Optional.ofNullable(abcRedisTemplate.opsForValue().get(keyForAbc(key)));
    }

    @Override
    public List<Abc> batchGetAbc(List<String> keys) {
        RedisJsonSerializer<Abc> serializer = (RedisJsonSerializer<Abc>) abcRedisTemplate.getValueSerializer();

        RedisCallback<List<Abc>> callback = connection -> {
            connection.openPipeline();

            keys.stream().forEach(key -> connection.get(keyForAbc(key).getBytes()));

            List<Object> abcs = connection.closePipeline();
            return abcs.stream()
                    .map(abc -> serializer.deserialize((byte[]) abc))
                    .collect(Collectors.toList());
        };

        return stringRedisTemplate.execute(callback);
    }
}