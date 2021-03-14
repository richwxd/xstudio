package io.github.xbeeant.spring.api.gateway.annotation;

import io.github.xbeeant.spring.api.gateway.service.ITimesService;
import io.github.xbeeant.spring.api.gateway.strategy.AbstractStrategy;

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
