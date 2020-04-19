package com.xstudio.aop.annotation;

import com.xstudio.aop.service.ILogService;
import com.xstudio.core.service.IAbstractService;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author xiaobiao
 * @version 2020/2/16
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 日志存储服务类
     *
     * @return ILogUserActionService服务
     */
    Class<?> service() default ILogService.class;

    /**
     * 行为名称
     *
     * @return String 行为名称
     */
    String actionName();

    boolean delete() default false;

    Class<?> selectService() default IAbstractService.class;

    String id() default "";
}
