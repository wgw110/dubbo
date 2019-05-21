package tv.mixiong.saas.account.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.saas.account.entity.walletFrozen;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class FrozenWalletDaoTest {

    @Autowired
    WalletFrozenDao walletFrozenDao;

    @Test
    public void insert(){
        walletFrozen frozenWallet = walletFrozen.builder().identity("2").amount(1).frozenBalance(100)
                .attachId(123L).type(1).sid(0L).updateTime(System.currentTimeMillis()).build();
        walletFrozenDao.insertUseGeneratedKeys(frozenWallet);
        System.out.println(frozenWallet);
    }

    @Test
    public void getFrozenBalance() {

        int balance = walletFrozenDao.getFrozenBalance("2");
        System.out.println(balance);
    }

    @Test
    public void getLastRecord() {
        System.out.println(walletFrozenDao.getLastRecord("2"));
    }
}