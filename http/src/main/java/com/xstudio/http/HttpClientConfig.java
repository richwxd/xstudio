package com.xstudio.http;

import lombok.Data;

/**
 * @author xiaobiao
 * @version 2020/3/1
 */
@Data
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
}
