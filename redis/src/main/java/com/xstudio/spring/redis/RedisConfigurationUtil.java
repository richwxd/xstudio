package com.xstudio.spring.redis;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多redis配置
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
public class RedisConfigurationUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisConfigurationUtil.class);

    /**
     * 获得redisTemplate
     *
     * @param redisProperties 复述,属性
     * @return {@link RedisTemplate&lt;Object, Object&gt;}
     */
    public static RedisTemplate<Object, Object> getRedisTemplate(BasicRedisProperties redisProperties) {
        RedisConfiguration configuration = null;
        RedisConnectionFactory connectionFactory;
        // redis 连接模式
        // 0 - standalone
        // 1 - sentinel
        // 2 -
        String redisMode = RedisConfigurationMode.STANDALONE;
        log.info("{} redis: {} {} {}", redisProperties.getName(), redisProperties.getHost(), redisProperties.getPort(), redisProperties.getDatabase());
        /* ========= 基本配置 ========= */
        // 独连模式
        if (null == redisProperties.getSentinel() && null == redisProperties.getCluster()) {
            configuration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
            ((RedisStandaloneConfiguration) configuration).setPassword(redisProperties.getPassword());
        }

        if (null != redisProperties.getSentinel() && null == redisProperties.getCluster()) {
            log.info("{} redis 使用哨兵模式配置{} ", redisProperties.getName(), redisProperties.getSentinel().getMaster());
            // 哨兵模式
            redisMode = RedisConfigurationMode.SENTINEL;
            List<String> sentinelNodes = redisProperties.getSentinel().getNodes();
            if (!CollectionUtils.isEmpty(sentinelNodes)) {
                configuration = new RedisSentinelConfiguration();
                ((RedisSentinelConfiguration) configuration).master(redisProperties.getSentinel().getMaster());
                List<RedisNode> sentinelRedisNodes = new ArrayList<>();
                for (String node : sentinelNodes) {
                    String[] hostAndPort = node.split(":");
                    sentinelRedisNodes.add(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
                }
                ((RedisSentinelConfiguration) configuration).setSentinels(sentinelRedisNodes);

                ((RedisSentinelConfiguration) configuration).setDatabase(redisProperties.getDatabase());
                if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
                    RedisPassword redisPassword = RedisPassword.of(redisProperties.getPassword());
                    ((RedisSentinelConfiguration) configuration).setPassword(redisPassword);
                }
            }
        }

        // cluster模式
        if (null != redisProperties.getCluster() && null == redisProperties.getSentinel()) {
            log.info("{} redis 使用cluster配置", redisProperties.getName());
            redisMode = RedisConfigurationMode.CLUSTER;
            List<String> clusterNodes = redisProperties.getCluster().getNodes();
            if (!CollectionUtils.isEmpty(clusterNodes)) {
                configuration = new RedisClusterConfiguration();
                List<RedisNode> redisNodes = new ArrayList<>();
                for (String node : clusterNodes) {
                    String[] hostAndPort = node.split(":");
                    redisNodes.add(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
                }
                ((RedisClusterConfiguration) configuration).setClusterNodes(redisNodes);

                ((RedisClusterConfiguration) configuration).setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
                if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
                    RedisPassword redisPassword = RedisPassword.of(redisProperties.getPassword());
                    ((RedisClusterConfiguration) configuration).setPassword(redisPassword);
                }
            }
        }

        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        BasicRedisProperties.Lettuce lettuce = redisProperties.getLettuce();
        if (null != lettuce.getPool()) {
            return lettucePool(redisProperties, configuration, genericObjectPoolConfig, lettuce);
        }

        BasicRedisProperties.Jedis jedis = redisProperties.getJedis();
        if (null != jedis.getPool()) {
            return jedisPool(redisProperties, configuration, redisMode, genericObjectPoolConfig, jedis);
        }

        return null;
    }

    /**
     * lettuce redis pool
     *
     * @param redisProperties         复述,属性
     * @param configuration           配置
     * @param genericObjectPoolConfig 通用对象池配置
     * @param lettuce                 生菜
     * @return {@link RedisTemplate&lt;Object, Object&gt;}
     */
    private static RedisTemplate<Object, Object> lettucePool(BasicRedisProperties redisProperties, RedisConfiguration configuration, GenericObjectPoolConfig<?> genericObjectPoolConfig, BasicRedisProperties.Lettuce lettuce) {
        RedisConnectionFactory connectionFactory;
        genericObjectPoolConfig.setMaxTotal(lettuce.getPool().getMaxActive());
        genericObjectPoolConfig.setMinIdle(lettuce.getPool().getMinIdle());
        genericObjectPoolConfig.setMaxIdle(lettuce.getPool().getMaxIdle());
        genericObjectPoolConfig.setMaxWaitMillis(lettuce.getPool().getMaxWait().toMillis());

        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        if (null != redisProperties.getTimeout()) {
            builder.commandTimeout(redisProperties.getTimeout());
        }

        connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        ((LettuceConnectionFactory) connectionFactory).afterPropertiesSet();
        /* ========= 创建 template ========= */
        log.info("{} 使用 lettuce 连接池 {}", redisProperties.getName(), configuration.getClass().getName());
        return createRedisTemplate(connectionFactory);
    }

    /**
     * jedis pool
     *
     * @param redisProperties         复述,属性
     * @param configuration           配置
     * @param redisMode               复述,模式
     * @param genericObjectPoolConfig 通用对象池配置
     * @param jedis                   能
     * @return {@link RedisTemplate&lt;Object, Object&gt;}
     */
    private static RedisTemplate<Object, Object> jedisPool(BasicRedisProperties redisProperties, RedisConfiguration configuration, String redisMode, GenericObjectPoolConfig<?> genericObjectPoolConfig, BasicRedisProperties.Jedis jedis) {
        RedisConnectionFactory connectionFactory;
        genericObjectPoolConfig.setMaxTotal(jedis.getPool().getMaxActive());
        genericObjectPoolConfig.setMinIdle(jedis.getPool().getMinIdle());
        genericObjectPoolConfig.setMaxIdle(jedis.getPool().getMaxIdle());
        genericObjectPoolConfig.setMaxWaitMillis(jedis.getPool().getMaxWait().toMillis());

        /* ========= jedis pool ========= */
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder builder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        assert configuration != null;
        switch (redisMode) {
            case "CLUSTER":
                connectionFactory = new JedisConnectionFactory((RedisClusterConfiguration) configuration, builder.build());
                break;
            case "SENTINEL":
                connectionFactory = new JedisConnectionFactory((RedisSentinelConfiguration) configuration, builder.build());
                break;
            default:
                connectionFactory = new JedisConnectionFactory((RedisStandaloneConfiguration) configuration, builder.build());
        }
        ((JedisConnectionFactory) connectionFactory).afterPropertiesSet();

        log.info("{} redis 使用jedis 连接池", redisProperties.getName());
        /* ========= 创建 template ========= */
        return createRedisTemplate(connectionFactory);
    }

    /**
     * redisTemplate
     * <p>
     * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
     *
     * @param redisConnectionFactory {@link RedisConnectionFactory}
     * @return {@link RedisTemplate}
     */
    public static RedisTemplate<Object, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setEnableDefaultSerializer(false);
        // 设置值（value）的序列化
        redisTemplate.setValueSerializer(new KryoSerializerAdapter<>());
        redisTemplate.setHashValueSerializer(new KryoSerializerAdapter<>());

        // 设置键（key）的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
