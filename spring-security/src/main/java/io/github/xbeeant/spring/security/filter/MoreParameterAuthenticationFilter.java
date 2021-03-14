package io.github.xbeeant.spring.security.filter;

import io.github.xbeeant.spring.security.LoginParamters;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private static String username = "username";

    /**
     * 密码参数
     */
    private static String password = "password";

    public MoreParameterAuthenticationFilter(AuthenticationManager authenticationManager, String pattern) {
        super(new AntPathRequestMatcher(pattern));
        this.setAuthenticationManager(authenticationManager);
    }

    protected MoreParameterAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected MoreParameterAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    /**
     * 参数设置密码
     *
     * @param password 密码参数
     */
    public static void setPassword(String password) {
        MoreParameterAuthenticationFilter.password = password;
    }

    /**
     * 用户名设置参数
     *
     * @param username 用户参数
     */
    public static void setUsername(String username) {
        MoreParameterAuthenticationFilter.username = username;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginParamters loginParams = new LoginParamters(request);
        Object usr = loginParams.get(MoreParameterAuthenticationFilter.username);
        Object pwd = loginParams.get(MoreParameterAuthenticationFilter.password);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usr, pwd);

        authRequest.setDetails(loginParams);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
