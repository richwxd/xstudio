package io.github.xbeeant.spring.api.gateway.service;

import io.github.xbeeant.spring.api.gateway.entity.Limit;

/**
 * 网关限制次数服务
 *
 * @author xiaobiao
 * @version 2020/2/28
 */
public interface ITimesService {
    /**
     * 获取限制次数
     *
     * @param key 标识
     * @return {@link Limit}
     */
    Limit times(String key);
}
