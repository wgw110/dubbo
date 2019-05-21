package tv.mixiong.api;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tv.mixiong.dubbo.service.ICommodityService;

@RestController
@RequestMapping("/v1/commodity")
@Slf4j
public class CommodityApi {

    @Reference
    private ICommodityService commodityService;

    @RequestMapping("/get")
    public Object getCommodity() {
        return commodityService.getCommodity(1L);
    }
}
