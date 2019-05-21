package tv.mixiong.school;


import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.SchoolLauncher;
import tv.mixiong.dubbo.service.IAuthService;
import tv.mixiong.saas.school.ds.AuthDs;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SchoolLauncher.class)
public class AuthServiceTest {

    @Reference
    private IAuthService authService;

    @Autowired
    AuthDs authDs;

    @Test
    public void testAuth(){
        authService.login(Optional.of("86"),"15910993585","13334","uid");
    }

    @Test
    public void user_transaction(){
        authDs.user_transaction();
    }
}
