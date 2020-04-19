package com.xstudio.aop.service;

import com.xstudio.aop.entity.LogEntity;

/**
 * 日志记录服务
 *
 * @author xiaobiao
 * @version 2020/2/16
 */
public interface ILogService {
    /**
     * 记录日志
     *
     * @param log 日志对象
     */
    void doLog(LogEntity log);
}
