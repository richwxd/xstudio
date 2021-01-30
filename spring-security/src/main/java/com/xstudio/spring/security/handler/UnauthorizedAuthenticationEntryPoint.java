package com.xstudio.spring.security.handler;

import com.xstudio.core.ApiResponse;
import com.xstudio.http.RequestUtil;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 * <p>
 * 区别于 AccessDenyHandler 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 *
 * @see RestAccessDeniedHandler
 */
public class UnauthorizedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiResponse<Object> msg = new ApiResponse<>(401, "尚未登录");
        response.setStatus(HttpStatus.SC_OK);
        RequestUtil.writeJson(response, msg);
    }
}
