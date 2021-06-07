package org.fastddd.sample.service.infra.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: frank.li
 * @date: 2021-06-06
 */
@Component
public class RedisCacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put(String key, String value, long expiredSeconds) {
        redisTemplate.opsForValue().set(key, value, expiredSeconds, TimeUnit.SECONDS);
    }
}
