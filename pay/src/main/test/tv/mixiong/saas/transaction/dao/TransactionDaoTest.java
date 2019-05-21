package tv.mixiong.saas.transaction.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.saas.pay.util.SNGenerator;
import tv.mixiong.saas.thirdpay.constants.TradeType;
import tv.mixiong.saas.transaction.constants.TransactionStatus;
import tv.mixiong.saas.transaction.constants.TransactionType;
import tv.mixiong.saas.transaction.entity.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class TransactionDaoTest {
    @Autowired
    private TransactionDao transactionDao;

    private String orderSn = "190313200943UHGUCJ0182";

    @Test
    public void insert(){
        Long currTime = System.currentTimeMillis();
        Transaction transaction = Transaction.builder().orderSn(orderSn).transactionSn(SNGenerator.generateTransactionSnByOrderSn(orderSn,true))
                .thirdSn("third_sn").payConfigId(1L).transactionType(TransactionType.PAY.getCode()).createTime(currTime)
                .expireTime(currTime).updateTime(currTime).transactionStatus(TransactionStatus.CREATE.getCode())
                .totalMoney(100).realMoney(80).plat(18).identity("214799").commodityId(1L).callback("callback")
                .tradeType(TradeType.JS.getType()).memo("memo").build();

        transactionDao.insertUseGeneratedKeys(transaction);
        System.out.println(transaction);
    }

    @Test
    public void findByTransactionSn() {
        Transaction transactionSn = transactionDao.getTransactionBySn("T2810JCUGHU349002313091CTMC");
        System.out.println(transactionSn);
    }

    @Test
    public void updateTransactionStatus() {
        int size = transactionDao.updateTransactionStatus(orderSn,"T2810JCUGHU349002313091NSPF",2,"thrid1",2,System.currentTimeMillis());
        System.out.println(size);
    }

    @Test
    public void findByOrderSnAndType() {
        System.out.println(transactionDao.listTransactionByOrderSnAndType(orderSn,TradeType.JS.getType()));
    }

    @Test
    public void updateMemo() {
        System.out.println(transactionDao.updateMemo("T2810JCUGHU349002313091NSPF","new memor"));
    }
}