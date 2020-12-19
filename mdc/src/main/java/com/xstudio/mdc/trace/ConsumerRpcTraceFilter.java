package com.xstudio.mdc.trace;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.xstudio.mdc.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * dubbo consumer mdc
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/16
 */
@SuppressWarnings("unused")
@Activate(group = {Constants.CONSUMER})
public class ConsumerRpcTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(Constant.MDC_KEY);
        if (StringUtils.isBlank(traceId)) {
            traceId = Constant.traceId();
        }

        RpcContext.getContext().setAttachment(Constant.MDC_KEY, traceId);
        return invoker.invoke(invocation);
    }
}
