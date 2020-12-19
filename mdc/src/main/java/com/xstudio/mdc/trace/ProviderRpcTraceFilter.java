package com.xstudio.mdc.trace;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.xstudio.mdc.Constant;
import org.slf4j.MDC;

/**
 * dubbo provider mdc
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/16
 */
@SuppressWarnings("unused")
@Activate(group = {Constants.PROVIDER}, order = 1)
public class ProviderRpcTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment(Constant.MDC_KEY);
        if (null == traceId) {
            traceId = Constant.traceId();
        }
        //设置日志traceId变量
        MDC.put(Constant.MDC_KEY, traceId);

        RpcContext.getContext().setAttachment(Constant.MDC_KEY, traceId);
        try {
            return invoker.invoke(invocation);
        } finally {
            MDC.remove(Constant.MDC_KEY);
        }
    }
}
