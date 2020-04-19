package com.xstudio.spring.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */
public class RedisUtil {
    /**
     * @param key           主键
     * @param liveTime      过期时间
     * @param timeUnit      过期时间单位
     * @param redisTemplate {@link RedisTemplate}
     * @return 当前增长值
     */
    public static Long incr(String key, long liveTime, TimeUnit timeUnit, RedisTemplate<Object, Object> redisTemplate) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        long increment = entityIdCounter.getAndIncrement();

        if (increment == 0) {
            // 初始设置过期时间
            entityIdCounter.expire(liveTime, timeUnit);
        }

        return increment;
    }
}
