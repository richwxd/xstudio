package io.github.xbeeant.aop.annotation;

import io.github.xbeeant.aop.service.ILogService;
import io.github.xbeeant.core.service.IAbstractService;

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
     * 行为名称
     *
     * @return String 行为名称
     */
    String actionName();

    /**
     * 是否删除操作
     *
     * @return {@link boolean}
     */
    boolean delete() default false;

    /**
     * 主键
     *
     * @return {@link String}
     * @see String
     */
    String id() default "";

    /**
     * 通过主键查询的服务
     *
     * @return {@link IAbstractService}
     */
    Class<?> selectService() default IAbstractService.class;

    /**
     * 日志存储服务类
     *
     * @return ILogUserActionService服务
     */
    Class<?> service() default ILogService.class;
}
