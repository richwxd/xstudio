package io.github.xbeeant.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/01
 */
public class InvalidAuthCodeException extends AuthenticationException {

    public InvalidAuthCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidAuthCodeException(String msg) {
        super(msg);
    }
}
