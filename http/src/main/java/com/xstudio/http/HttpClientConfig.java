package com.xstudio.http;

/**
 * @author xiaobiao
 * @version 2020/3/1
 */
public class HttpClientConfig {
    /**
     * 连接超时时间(单位毫秒): 10S
     */
    private static int httpConnectTimeout = 10000;
    /**
     * socket读写超时时间(单位毫秒)
     */
    private static int httpSocketTimeout = 10000;


    private static int httpPerRouteSize = 50;

    private static int httpMaxPoolSize = 200;

    private static int httpMonitorInterval = 3000;

    private static int httpIdleTimeout = 5000;

    private static int retryTimes = 3;

    public static int getHttpConnectTimeout() {
        return httpConnectTimeout;
    }

    public static void setHttpConnectTimeout(int httpConnectTimeout) {
        HttpClientConfig.httpConnectTimeout = httpConnectTimeout;
    }

    public static int getHttpSocketTimeout() {
        return httpSocketTimeout;
    }

    public static void setHttpSocketTimeout(int httpSocketTimeout) {
        HttpClientConfig.httpSocketTimeout = httpSocketTimeout;
    }

    public static int getHttpMaxPoolSize() {
        return httpMaxPoolSize;
    }

    public static void setHttpMaxPoolSize(int httpMaxPoolSize) {
        HttpClientConfig.httpMaxPoolSize = httpMaxPoolSize;
    }

    public static int getHttpMonitorInterval() {
        return httpMonitorInterval;
    }

    public static void setHttpMonitorInterval(int httpMonitorInterval) {
        HttpClientConfig.httpMonitorInterval = httpMonitorInterval;
    }

    public static int getHttpIdleTimeout() {
        return httpIdleTimeout;
    }

    public static void setHttpIdleTimeout(int httpIdleTimeout) {
        HttpClientConfig.httpIdleTimeout = httpIdleTimeout;
    }

    public static int getRetryTimes() {
        return retryTimes;
    }

    public static void setRetryTimes(int retryTimes) {
        HttpClientConfig.retryTimes = retryTimes;
    }

    /**
     * 获取 httpPerRouteSize.
     *
     * @return httpPerRouteSize 值
     */
    public static int getHttpPerRouteSize() {
        return httpPerRouteSize;
    }

    /**
     * 设置 httpPerRouteSize.
     *
     * <p>通过 getHttpPerRouteSize() 获取 httpPerRouteSize</p>
     *
     * @param httpPerRouteSize httpPerRouteSize
     */
    public static void setHttpPerRouteSize(int httpPerRouteSize) {
        HttpClientConfig.httpPerRouteSize = httpPerRouteSize;
    }
}
