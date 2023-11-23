package com.idle.fish.tamplate.basic.user.dubbo;

import com.idle.fish.tamplate.basic.user.BasicUser;
import com.idle.fish.tamplate.basic.user.CurrentUser;
import com.idle.fish.tamplate.basic.user.constant.UserConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Optional;

/**
 * 消费者在调用dubbo接口之前，把用户信息写入RpcContext
 * 服务提供者在接收到dubbo接口调用之前，把RpcContext中的用户信息写入ThreadLocal
 *
 * @author idle fish
 * @since 2023/11/23
 */
@Slf4j
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER}, order = -10000)
public class UserDubboFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        if (rpcContext.isConsumerSide()) {
            Optional.ofNullable(CurrentUser.getUser())
                    .ifPresent(x -> rpcContext.setAttachment(UserConstants.DUBBO_ATTACHMENT_USER, x));
            log.debug("set current thread user to rpc context attachment");
        } else if (rpcContext.isProviderSide()) {
            Optional.ofNullable(rpcContext.getObjectAttachment(UserConstants.DUBBO_ATTACHMENT_USER))
                    .ifPresent(x -> CurrentUser.set((BasicUser) x));
            log.debug("read user from rpc context attachment and set user to thread local");
        }
        return invoker.invoke(invocation);
    }
}
