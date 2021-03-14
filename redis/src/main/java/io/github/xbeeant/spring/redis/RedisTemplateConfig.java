package io.github.xbeeant.spring.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis template 序列化配置
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        return RedisConfigurationUtil.createRedisTemplate(connectionFactory);
    }
}
