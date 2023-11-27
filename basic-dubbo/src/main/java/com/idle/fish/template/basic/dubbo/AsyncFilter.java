package com.idle.fish.template.basic.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * - 异步的坑：在一次调用过程中，如果 A->B 是异步的，那么 B->C 也会是异步的，因为RpcContext中被标记为async
 * - 需要通过Filter在生产者移除异步标记
 *
 * @author idle fish
 * @since 2023/11/23
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER})
public class AsyncFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().removeAttachment(Constants.ASYNC_KEY);
        log.debug("remove async key");
        return invoker.invoke(invocation);
    }
}