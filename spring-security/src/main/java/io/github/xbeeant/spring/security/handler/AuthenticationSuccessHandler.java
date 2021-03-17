package io.github.xbeeant.spring.security.handler;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.http.Requests;
import io.github.xbeeant.spring.security.LoginUser;
import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/01
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        // 输出登录提示信息
        response.setStatus(HttpStatus.SC_OK);
        ApiResponse<Object> msg = new ApiResponse<>();
        msg.setData(setData(authentication));
        // 返回json
        Requests.writeJson(response, msg);
    }

    public Object setData(Authentication authentication) {
        if (authentication.getPrincipal() instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            return loginUser.getDetails();
        }

        return null;
    }
}
