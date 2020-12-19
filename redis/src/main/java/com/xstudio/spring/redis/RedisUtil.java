package com.xstudio.spring.redis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
@Component
public class RedisUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private RedisUtil() {

    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key  缓存键  redis主键
     * @param time 过期时间 单位： 秒
     */
    public static void expire(final Object key, final long time) {
        getRedisTemplate().expire(key, time, TimeUnit.SECONDS);
    }

    private static RedisTemplate<Object, Object> getRedisTemplate() {
        return applicationContext.getBean("redisTemplate", RedisTemplate.class);
    }

    /**
     * 获取缓存值
     *
     * @param key key
     * @param <T> 返回值类型
     * @return {@link T}
     */
    public static <T> T get(Object key) {
        return (T) getRedisTemplate().opsForValue().get(key);
    }

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

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   缓存键   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public  static List<Object> getList(Object key, int start, int end) {
        return getRedisTemplate().opsForList().range(key, start, end);
    }

    public static void hasPut(Object key, Object hashKey, Object value) {
        getRedisTemplate().opsForHash().put(key, hashKey, value);
    }

    /**
     * 写入缓存
     *
     * @param key  缓存键
     * @param list 列表
     * @return {@link Long}
     */
    public static Long leftPushAll(Object key, List<String> list) {
        return getRedisTemplate().opsForList().leftPushAll(key, list);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 缓存键
     */
    public static void remove(final Object... keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key 缓存键
     */
    public static void remove(final Object key) {
        if (hasKey(key)) {
            getRedisTemplate().delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key key
     * @return boolean
     */
    public  static boolean hasKey(final Object key) {
        return getRedisTemplate().hasKey(key);
    }

    /**
     * 删除列表
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   缓存键
     * @param i     参数
     * @param value the value
     * @return {@link Long}
     */
    public static Long removeList(Object key, long i, String value) {
        return getRedisTemplate().opsForList().remove(key, i, value);
    }

    /**
     * 写入缓存
     *
     * @param key        缓存键
     * @param value      值
     * @param expireTime 过期时间（单位：s)
     */
    public static void set(final Object key, Object value, Long expireTime) {
        ValueOperations<Object, Object> valueOperations = getRedisTemplate().opsForValue();
        valueOperations.set(key, value);
        getRedisTemplate().expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存
     *
     * @param key   缓存键
     * @param value 值
     */
    public static void set(final String key, Object value) {
        ValueOperations<Object, Object> operations = getRedisTemplate().opsForValue();
        operations.set(key, value);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisUtil.applicationContext = applicationContext;
    }
}
