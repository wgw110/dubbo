package tv.mixiong.ds.service.im;

import com.alibaba.dubbo.rpc.RpcContext;
import com.taobao.txc.common.TxcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tv.mixiong.ds.cache.im.ImAdminAccountCache;
import tv.mixiong.ds.dao.entity.ImAdminAccount;
import tv.mixiong.ds.dao.im.ImAdminAccountDao;
import tv.mixiong.ds.dto.im.ImAdminAccountDto;

/**
 * -------------------------------------
 * -------------------------------------
 */
@Service("imAdminAccountDs")
@Slf4j
public class ImAdminAccountDs {

    @Autowired
    private ImAdminAccountDao imAdminAccountDao;

    @Autowired
    private ImAdminAccountCache imAdminAccountCache;


    public ImAdminAccountDto getImAdminAccount(int productId) {
        ImAdminAccountDto imAdminAccountDto = imAdminAccountCache.get(productId);
        if (imAdminAccountDto == null) {
            imAdminAccountDto = ImAdminAccountDto.from(imAdminAccountDao.findByProductId(productId));
            imAdminAccountCache.set(productId, imAdminAccountDto);
        }
        return imAdminAccountDto;
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class, value = "transactionManager")
    public void im_transaction(int i) throws  Exception{
        String xid = RpcContext.getContext().getAttachment("xid");
        String bc = RpcContext.getContext().getAttachment("beginCount");
        int beginCount = (bc == null ? 0 : Integer.parseInt(bc));
        TxcContext.bind(xid,null);
        TxcContext.setBegin(beginCount);
        ImAdminAccount imAdminAccount = new ImAdminAccount();
        imAdminAccount.setImAdmin("10001");
        imAdminAccount.setSdkAppId("defscscefdrfrdfdf");
        imAdminAccount.setCrateTime(System.currentTimeMillis());
        imAdminAccount.setProductId(3);
        imAdminAccount.setUpdateTime(System.currentTimeMillis());
        imAdminAccountDao.insertUseGeneratedKeys(imAdminAccount);
        ImAdminAccount adminAccount = imAdminAccountDao.findByProductId(3);
        if(i==4){
            throw  new Exception("error....");
        }
        TxcContext.unbind();

    }
}
