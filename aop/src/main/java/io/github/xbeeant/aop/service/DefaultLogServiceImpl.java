package io.github.xbeeant.aop.service;

import io.github.xbeeant.aop.entity.LogEntity;
import io.github.xbeeant.core.JsonUtil;
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
