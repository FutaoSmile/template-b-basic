package com.idle.fish.template.basic.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * 写这个拦截器的原因是，领导要求减少error日志的打印
 * 如果provider抛出异常，会被打印出来，但是可以通过设置 <dubbo:provider filter="-exception,quietExceptionFilter"/> 来关闭dubbo默认的异常拦截器打印
 * 但是为了感知异常的发生，所以把日志级别设置为了warn
 *
 * @author idle fish
 * @since 2023/11/23
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class QuietExceptionFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                log.warn("异常", result.getException());
            }
            return result;
        } catch (RpcException e) {
            // 在这里可以对异常进行处理，比如记录日志而不打印到控制台
            // 如果不想打印异常信息，可以在这里控制
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}