package tv.mixiong.saas.account.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.saas.account.entity.Wallet;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class WalletDaoTest {

    @Autowired
    private WalletDao walletDao;

    @Test
    public void testInsert(){
        Wallet wallet = Wallet.builder().identity("2").amount(1).balance(10).type(1).sn("34353").
                title("test").updateTime(System.currentTimeMillis()).commodityId(1L).sid(0).build();
        walletDao.insertUseGeneratedKeys(wallet);
        System.out.println(wallet);
    }

    @Test
    public void listWallet() {
        List<Wallet> wallets = walletDao.listWallet("2",null , System.currentTimeMillis(), 1);
        System.out.println(wallets);
    }

    @Test
    public void getBalance() {
        int balance = walletDao.getBalance("2");
        System.out.println(balance);
    }

    @Test
    public void getLastedRecord() {
        Wallet wallet = walletDao.getLastedRecord("2");
        System.out.println(wallet);
    }

    @Test
    public void updateLastedRecord() {
        walletDao.updateLastedRecord("2",1L,100);
    }

    @Test
    public void updateParentRecord() {
        walletDao.updateParentRecord("2",1L,2L);
    }
}