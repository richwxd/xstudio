package com.xstudio.aop.annotation;

import java.lang.annotation.*;

/**
 * 铭感字符串过滤
 *
 * @author xiaobiao
 * @version 2020/2/16
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnEscapeField {
    /**
     * 无需被转义的字段集
     *
     * @return String[]
     */
    String[] fields();
}
