package com.xstudio.http;

/**
 * @author xiaobiao
 * @version 2020/3/1
 */
public class HttpClientConfig {
    /**
     * 连接超时时间(单位毫秒): 10S
     */
    static int httpConnectTimeout = 10000;
    /**
     * socket读写超时时间(单位毫秒)
     */
    static int httpSocketTimeout = 10000;

    static int httpMaxPoolSize = 200;

    static int httpMonitorInterval = 3000;

    static int httpIdleTimeout = 5000;

    static int retryTimes = 5;

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
}
