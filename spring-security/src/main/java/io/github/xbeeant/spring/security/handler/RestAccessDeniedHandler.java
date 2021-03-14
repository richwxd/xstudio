package io.github.xbeeant.spring.security.handler;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.http.RequestUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决认证通过的用户，访问无权限资源时的异常
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResponse<Object> msg = new ApiResponse<>(405, "尚未获得授权");
        RequestUtil.writeJson(response, msg);
    }
}
