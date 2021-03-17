package io.github.xbeeant.spring.security.handler;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.http.Requests;
import io.github.xbeeant.spring.security.exception.ExpiredAuthCodeException;
import io.github.xbeeant.spring.security.exception.InvalidAuthCodeException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证失败处理器
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/01
 */
public class AuthenticationFailedHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        ApiResponse<String> msg = new ApiResponse<>();
        msg.setCode(1);
        msg.setMsg("账号或密码错误");
        if (exception instanceof UsernameNotFoundException) {
            msg.setCode(2);
        } else if (exception instanceof BadCredentialsException) {
            msg.setCode(3);
        } else if (exception instanceof InvalidAuthCodeException) {
            msg.setCode(4);
            msg.setMsg("验证码错误");
        } else if (exception instanceof LockedException) {
            msg.setCode(10);
        } else if (exception instanceof DisabledException) {
            msg.setCode(11);
        } else if (exception instanceof CredentialsExpiredException) {
            msg.setCode(12);
        } else if (exception instanceof ExpiredAuthCodeException) {
            msg.setCode(13);
            msg.setMsg("验证码已过期");
        } else if (exception instanceof AccountExpiredException) {
            msg.setCode(14);
            msg.setMsg("登录已过期");
        } else if (null != exception.getMessage()) {
            msg.setMsg(exception.getMessage());
        }
        Requests.writeJson(response, msg);
    }
}
