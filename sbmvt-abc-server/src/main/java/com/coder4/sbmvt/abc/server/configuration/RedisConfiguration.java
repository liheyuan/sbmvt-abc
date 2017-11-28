/**
 * @(#)RedisConfiguration.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 coder4.com. All rights reserved.
 * CODER4.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt.abc.server.configuration;

import com.coder4.sbmvt.JedisConnectionFactoryBuilder;
import com.coder4.sbmvt.MyRedisProperties;
import com.coder4.sbmvt.RedisJsonSerializer;
import com.coder4.sbmvt.abc.server.data.Abc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author coder4
 */
@Configuration
public class RedisConfiguration {

    @Bean
    @Qualifier("abcRedisRedisProperties")
    @ConfigurationProperties(prefix = "sbmvtAbc.redis")
    public MyRedisProperties abcRedisProperties() {
        return new MyRedisProperties();
    }

    @Bean
    public JedisConnectionFactory abcJedisConnectionFactory(
            @Qualifier("abcRedisProperties") MyRedisProperties properties) {
        return JedisConnectionFactoryBuilder.build(properties);
    }

    @Bean
    public RedisTemplate<String, Abc> abcRedisTemplate(
            @Qualifier("abcJedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Abc> template = new RedisTemplate<String, Abc>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisJsonSerializer(Abc.class));
        return template;
    }

}