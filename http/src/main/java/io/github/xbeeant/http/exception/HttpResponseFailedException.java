package io.github.xbeeant.http.exception;

/**
 * HTTP 请求返回异常
 *
 * @author xiaobiao
 * @version 2021/5/17
 */
public class HttpResponseFailedException extends RuntimeException {
    public HttpResponseFailedException(String message, Exception e) {
        super(message, e);
    }
}
