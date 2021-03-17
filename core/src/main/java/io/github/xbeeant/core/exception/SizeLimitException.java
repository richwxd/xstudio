package io.github.xbeeant.core.exception;

/**
 * 超出长度限制
 *
 * @author xiaobiao
 * @date 2021/03/17
 */
public class SizeLimitException extends RuntimeException {

    public SizeLimitException(String message) {
        super(message);
    }

    public SizeLimitException() {
        super();
    }
}
