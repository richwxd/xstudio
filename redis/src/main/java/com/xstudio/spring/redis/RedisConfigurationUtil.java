package com.xstudio.spring.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger log = LogManager.getLogger(RedisConfigurationUtil.class);

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
            log.info("{} redis 使用独连模式配置", redisProperties.getName());
            configuration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
            ((RedisStandaloneConfiguration) configuration).setPassword(redisProperties.getPassword());
        }

        if (null != redisProperties.getSentinel() && null == redisProperties.getCluster()) {
            log.info("{} redis 使用哨兵模式配置{} {}", redisProperties.getName(), redisProperties.getSentinel().getMaster(), JSON.toJSONString(redisProperties.getSentinel().getNodes()));
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

        BasicRedisProperties.Jedis jedis = redisProperties.getJedis();
        if (null != jedis.getPool()) {
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

        return null;
    }

    /**
     * json 实现 redisTemplate
     * <p>
     * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
     *
     * @param redisConnectionFactory {@link RedisConnectionFactory}
     * @return {@link RedisTemplate}
     */
    public static RedisTemplate<Object, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
//        ParserConfig.getGlobalInstance().addAccept("com.xstudio");
        redisTemplate.setEnableDefaultSerializer(false);
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(String.class));
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer<>(String.class));

        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
