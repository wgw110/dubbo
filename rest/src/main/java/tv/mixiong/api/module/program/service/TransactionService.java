package tv.mixiong.api.module.program.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.taobao.txc.client.aop.annotation.TxcTransaction;
import com.taobao.txc.common.TxcContext;
import org.springframework.stereotype.Service;
import tv.mixiong.dubbo.service.IAuthService;
import tv.mixixiong.im.service.IPushService;

@Service
public class TransactionService {

    @Reference
    private IPushService pushService;

    @Reference
    private IAuthService authService;

   @TxcTransaction(timeout = 60000 * 3)
    public void testTransaction() throws Exception{
       String xid = TxcContext.getCurrentXid();
       int beginCount = TxcContext.getBeginCount();
       //通过RpcContext将xid传到服务端
       RpcContext.getContext().setAttachment("xid", xid);
       RpcContext.getContext().setAttachment("beginCount", String.valueOf(beginCount));
       authService.saveUser();
       RpcContext.getContext().setAttachment("xid",xid);
       pushService.saveIm(3);
    }

}
