package tv.mixiong.dubbo.service;

import tv.mixiong.dto.CommodityDto;
import tv.mixiong.entity.Commodity;

public interface ICommodityService {

     CommodityDto getCommodity(Long id );

     void createCommodity(Commodity commodity);
}
