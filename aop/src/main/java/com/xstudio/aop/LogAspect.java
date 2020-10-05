package com.xstudio.aop;

import com.xstudio.aop.annotation.Log;
import com.xstudio.aop.entity.LogEntity;
import com.xstudio.aop.service.ILogService;
import com.xstudio.core.JsonUtil;
import com.xstudio.core.ApiResponse;
import com.xstudio.core.service.IAbstractService;
import com.xstudio.http.RequestUtil;
import com.xstudio.spring.web.SpringContextProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static Parser uaParser;

    {
        try {
            uaParser = new Parser();
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 切面注解
     */
    @Pointcut("@annotation(com.xstudio.aop.annotation.Log)")
    public void logPointcut() {
        // 定义切面annotation注解方法
    }

    /**
     * 环绕通知
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return Object 方法执行的返回对象
     * @throws Throwable aop异常{@link Throwable}
     */
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 日志服务获取
        Log annotation = method.getAnnotation(Log.class);
        if (null == annotation) {
            return joinPoint.proceed();
        }

        // 获取输入参数
        Object[] arg = joinPoint.getArgs();

        // 请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = AspectJUtils.getMethodName(joinPoint);


        ILogService logService = (ILogService) SpringContextProvider.getBean((annotation).service());

        // 是否删除
        boolean doSelect = annotation.delete();
        if (doSelect) {
            IAbstractService selectService = (IAbstractService) SpringContextProvider.getBean((annotation).selectService());
            String id = annotation.id();
            Map<String, Object> parse = JsonUtil.toMap((String) arg[0]);
            ApiResponse<Object> apiResponse = selectService.selectByPrimaryKey(parse.get(id));
            arg[0] = apiResponse.getData();
        }

        Date actionTime = new Date();
        long beginTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long time = System.nanoTime() - beginTime;

        // 存储日志
        LogEntity log = new LogEntity();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.setIp(RequestUtil.getIp(request));
        String userAgent = RequestUtil.getUserAgent(request);
        if (null != userAgent) {
            Client client = uaParser.parse(userAgent);
            log.setAgent(client);
        }


        if (null != arg && arg.length == 1) {
            log.setInputs(arg[0]);
        } else {
            log.setInputs(arg);
        }

        log.setCost(time);
        log.setOutputs(result);
        log.setClassName(className);
        log.setMethodName(methodName);
        log.setActionName(annotation.actionName());
        log.setTime(actionTime);
        try {
            logService.doLog(log);
        } catch (Exception e) {
            logger.error("日志记录异常", e);
        }

        return result;
    }
}
