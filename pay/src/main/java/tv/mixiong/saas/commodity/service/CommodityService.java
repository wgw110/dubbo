package tv.mixiong.saas.commodity.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tv.mixiong.dto.CommodityDto;
import tv.mixiong.dubbo.service.ICommodityService;
import tv.mixiong.entity.Commodity;
import tv.mixiong.saas.commodity.ds.CommodityDs;

@Service(interfaceClass = ICommodityService.class)
public class CommodityService implements ICommodityService{

    @Autowired
    private CommodityDs  commodityDs;

    @Override
    public CommodityDto getCommodity(Long id) {
         return commodityDs.getCommodity(id);
    }

    @Override
    public void createCommodity(Commodity commodity) {
        commodityDs.addCommodity(commodity);
    }
}
