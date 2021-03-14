package io.github.xbeeant.mdc.trace;

import io.github.xbeeant.mdc.Constant;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request mdc
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/16
 */
@SuppressWarnings("unused")
public class RequestMdcInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(Constant.MDC_KEY);
        if (null == traceId) {
            traceId = Constant.traceId();
        }
        MDC.put(Constant.MDC_KEY, traceId);
        response.addHeader(Constant.MDC_KEY, traceId);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constant.MDC_KEY);
        super.afterCompletion(request, response, handler, ex);
    }
}
