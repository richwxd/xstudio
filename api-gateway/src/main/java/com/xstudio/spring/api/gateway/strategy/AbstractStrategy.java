package com.xstudio.spring.api.gateway.strategy;

import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 限流策略
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
public abstract class AbstractStrategy {
    /**
     * 策略校验
     *
     * @param key           唯一标识
     * @param request       当前请求
     * @param times         最大请求次数
     * @param unit          单位时间
     * @param redisTemplate {@link RedisTemplate}
     * @return 是否限制
     */
    public abstract boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate);
}
