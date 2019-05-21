package tv.mixiong.saas.transfer.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.saas.transfer.constants.TransferStatus;
import tv.mixiong.saas.transfer.entity.Transfer;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class TransferDaoTest {

    @Autowired
    private TransferDao transferDao;

    @Test
    public void insert(){
        Long currTime = System.currentTimeMillis();
        Transfer transfer = Transfer.builder().transferSn("transferSn").identity("2").transferAccount("openId")
                .amount(100).realAmount(80).tax(0).taxBefore(100).procedureAmount(20).transferStatus(0)
                .createTime(currTime).updateTime(currTime).thirdSn("third_sn").build();
        transferDao.insertUseGeneratedKeys(transfer);
    }

    @Test
    public void findByTransferSn() {
        System.out.println(transferDao.getTransferBySn("transferSn"));
    }

    @Test
    public void updateTransferStatus() {
        System.out.println(transferDao.updateTransferStatus("transferSn",TransferStatus.TRANSFERED.getCode(),"thrid1",System.currentTimeMillis()));
    }

    @Test
    public void listTransfer() {
        System.out.println(transferDao.listTransfer("2",null,System.currentTimeMillis()));
    }
}