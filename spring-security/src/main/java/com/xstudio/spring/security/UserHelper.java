package com.xstudio.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前用户类
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2021/01/03
 */
public class UserHelper {
    private UserHelper() {

    }

    /**
     * 得到当前的主
     * 获取当前用户
     *
     * @return {@link Object}
     */
    public static Object getCurrentPrincipal() {
        return getCurrentUser().getPrincipal();
    }

    /**
     * 获取当前用户
     *
     * @return {@link Authentication}
     */
    public static Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
