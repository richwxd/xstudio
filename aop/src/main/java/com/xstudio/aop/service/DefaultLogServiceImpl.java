package com.xstudio.aop.service;

import com.alibaba.fastjson.JSON;
import com.xstudio.aop.entity.LogEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
@Service
public class DefaultLogServiceImpl implements ILogService {
    public static Logger logger = LogManager.getLogger(DefaultLogServiceImpl.class);

    @Override
    public void doLog(LogEntity log) {
        logger.info("{}", JSON.toJSONString(log));
    }
}
