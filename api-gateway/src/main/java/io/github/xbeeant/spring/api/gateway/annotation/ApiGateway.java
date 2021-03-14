package io.github.xbeeant.spring.api.gateway.annotation;

import java.lang.annotation.*;

/**
 * API网关注解,仅用于控制器层
 *
 * @author xiaobiao
 * @version 2020/2/12
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiGateway {

    ApiGatewayStrategy[] strategies() default {};

    String redisTemplateBean() default "redisTemplate";
}
