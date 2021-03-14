package io.github.xbeeant.spring.redis;

/**
 * redis模式配置
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
public class RedisConfigurationMode {
    private RedisConfigurationMode(){

    }
    /**
     * 独连
     */
    public static final String STANDALONE = "STANDALONE";
    /**
     * 哨兵
     */
    public static final String SENTINEL = "SENTINEL";
    /**
     * 集群
     */
    public static final String CLUSTER = "CLUSTER";

}
