package com.xstudio.spring.api.gateway.strategy;

import com.xstudio.http.RequestUtil;
import com.xstudio.spring.redis.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * IP策略
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
@Log4j2
public class IpStrategy extends AbstractStrategy {
    @Override
    public boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate) {
        String ip = RequestUtil.getIp(request);
        Long incr = RedisUtil.incr(key + ip, 1, unit, redisTemplate);
        return incr < times;
    }
}
