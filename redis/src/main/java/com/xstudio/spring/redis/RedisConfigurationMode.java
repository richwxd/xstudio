package com.xstudio.spring.redis;

/**
 * redis模式配置
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
public class RedisConfigurationMode {
    /**
     * 独连
     */
    public static String STANDALONE = "STANDALONE";
    /**
     * 哨兵
     */
    public static String SENTINEL = "SENTINEL";
    /**
     * 集群
     */
    public static String CLUSTER = "CLUSTER";

}
