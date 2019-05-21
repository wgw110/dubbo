package tv.mixiong.api;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tv.mixixiong.pay.IPayService;

@RestController
@RequestMapping("/v1/pay")
@Slf4j
public class OrderApi {
    @Reference
    private IPayService iPayService;

    @RequestMapping("/init")
    public String init(){
        return iPayService.pay();
    }
}
