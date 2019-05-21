package tv.mixiong.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

@Activate(group = {"consumer", "provider"})
public class DubboContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String var = RpcContext.getContext().getAttachment("a");
        //todo 其他相关处理
        return invoker.invoke(invocation);
    }
}