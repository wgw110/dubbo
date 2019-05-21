package tv.mixiong.saas.school.ds;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.taobao.txc.common.TxcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tv.mixiong.saas.school.dao.UserDao;
import tv.mixiong.saas.school.entity.User;


@Service
@Slf4j
public class AuthDs {

    @Autowired
    private UserDao userDao;

    public User findByMobile(final String nation, final String mobile) {
        return userDao.findByMobile(nation, mobile);
    }

    public User createUserByMobile(final String nation, final String mobile) {
        User user = new User();
        user.setCreateTime(System.currentTimeMillis());
        user.setMobile(mobile);
        user.setNation(nation);
        userDao.insertUseGeneratedKeys(user);
        return user;
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class, value = "transactionManager")
    public void user_transaction(){
        String xid = RpcContext.getContext().getAttachment("xid");
        String bc = RpcContext.getContext().getAttachment("beginCount");
        int beginCount = (bc == null ? 0 : Integer.parseInt(bc));
        TxcContext.bind(xid,null);
        TxcContext.setBegin(beginCount);
        User user = new User();
        user.setCreateTime(System.currentTimeMillis());
        user.setMobile("13552576724234");
        user.setNation("csdn");
        userDao.insertUseGeneratedKeys(user);

        User user1 = userDao.findByMobile("86","15910993585");
        TxcContext.unbind();
    }
}
