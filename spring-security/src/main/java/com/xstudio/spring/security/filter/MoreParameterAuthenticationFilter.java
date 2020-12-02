package com.xstudio.spring.security.filter;

import com.xstudio.spring.security.LoginParamters;
import com.xstudio.spring.security.exception.InvalidAuthCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支持更多登录参数
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/02
 */
public class MoreParameterAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 用户名参数
     */
    private static String USERNAME_PARAM = "username";

    /**
     * 密码参数
     */
    private static String PASSWORD_PARAM = "password";

    /**
     * 用户名设置参数
     *
     * @param usernameParam 用户参数
     */
    public static void setUsernameParam(String usernameParam) {
        USERNAME_PARAM = usernameParam;
    }

    /**
     * 参数设置密码
     *
     * @param passwordParam 密码参数
     */
    public static void setPasswordParam(String passwordParam) {
        PASSWORD_PARAM = passwordParam;
    }

    protected MoreParameterAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected MoreParameterAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginParamters loginParams = new LoginParamters(request);
        Object username = loginParams.get(USERNAME_PARAM);
        Object password = loginParams.get(PASSWORD_PARAM);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        authRequest.setDetails(loginParams);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
