package io.github.xbeeant.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码过期异常
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/01
 */
public class ExpiredAuthCodeException extends AuthenticationException {

    public ExpiredAuthCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExpiredAuthCodeException(String msg) {
        super(msg);
    }
}
