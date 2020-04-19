package com.xstudio.spring.api.gateway.annotation;

import com.xstudio.spring.api.gateway.service.ITimesService;
import com.xstudio.spring.api.gateway.strategy.AbstractStrategy;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2020/2/12
 */

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiGatewayStrategy {
    int times() default 10;

    Class<? extends ITimesService> timesService() default ITimesService.class;

    TimeUnit unit() default TimeUnit.SECONDS;

    Class<? extends AbstractStrategy> type();
}
