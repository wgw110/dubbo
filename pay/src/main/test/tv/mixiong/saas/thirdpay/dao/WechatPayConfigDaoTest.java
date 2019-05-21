package tv.mixiong.saas.thirdpay.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class WechatPayConfigDaoTest {

    @Autowired
    private WechatPayConfigDao wechatPayConfigDao;

    @Test
    public void getWechatPayConfigBySchool() {
        System.out.println(wechatPayConfigDao.getWechatPayConfigBySchool("2"));

    }
}