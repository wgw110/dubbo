package tv.mixiong.test;


import lombok.extern.slf4j.Slf4j;
import tv.mixiong.Application;
import tv.mixiong.ds.dto.im.ImAdminAccountDto;
import tv.mixiong.ds.service.im.ImAdminAccountDs;
import tv.mixiong.ds.service.im.ImUserSignDs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.ds.service.uc.UserCenterDs;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "develop")
@SpringBootTest(classes = Application.class)
@Slf4j
public class DaoTest {

    @Autowired
    private ImUserSignDs imUserSignDs;

    @Autowired
    private ImAdminAccountDs adminAccountDs;

    @Autowired
    private UserCenterDs userCenterDs;

    @Test
    public void tt(){
        ImAdminAccountDto imAdminAccountDto = adminAccountDs.getImAdminAccount(1);
        System.out.println(imAdminAccountDto);
    }

    @Test
    public void test2(){
        try {
            adminAccountDs.im_transaction(4);
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
