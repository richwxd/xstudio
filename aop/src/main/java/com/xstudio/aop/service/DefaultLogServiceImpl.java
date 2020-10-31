package com.xstudio.aop.service;

import com.xstudio.aop.entity.LogEntity;
import com.xstudio.core.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
@Service
public class DefaultLogServiceImpl implements ILogService {
    private static Logger logger = LoggerFactory.getLogger(DefaultLogServiceImpl.class);

    @Override
    public void doLog(LogEntity log) {
        if (logger.isInfoEnabled()) {
            logger.info("{}", JsonUtil.toJsonString(log));
        }
    }
}
