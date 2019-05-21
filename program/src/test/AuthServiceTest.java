
import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.ProviderLauncher;
import tv.mixiong.dubbo.exception.ServiceException;
import tv.mixiong.dubbo.service.IProgramService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderLauncher.class)
public class AuthServiceTest {

    @Reference
    private IProgramService authService;

    @Test
    public void testAuth(){
        try {
            authService.sayHello("111");
        }catch (ServiceException e){
            System.out.println("-=-=-=-=");
        }

    }

    @Test
    public void tt(){

    }
}
