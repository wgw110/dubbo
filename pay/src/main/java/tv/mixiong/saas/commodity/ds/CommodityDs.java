package tv.mixiong.saas.commodity.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.dto.CommodityDto;
import tv.mixiong.saas.commodity.cache.CommodityCache;
import tv.mixiong.saas.commodity.dao.CommodityDao;
import tv.mixiong.entity.Commodity;

import java.util.List;

@Component
public class CommodityDs {

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private CommodityCache commodityCache;

    public void addCommodity(Commodity commodity) {
        Long currTime = System.currentTimeMillis();
        Commodity oldCommodity = commodityDao.findByTypeAndAttachId(commodity.getCommodityType(),commodity.getAttachId());
        if (oldCommodity == null){
            commodity.setCreateTime(currTime);
            commodityDao.insertSelective(commodity);
        }else{
            commodity.setId(oldCommodity.getId());
            commodity.setUpdateTime(currTime);
            commodityDao.updateByPrimaryKey(commodity);
            commodityCache.drop(oldCommodity.getId());
        }
    }

    public CommodityDto getCommodity(Long commodityId){
        CommodityDto commodity = commodityCache.get(commodityId);
        if(commodity == null){
            commodity = parse(commodityDao.selectByPrimaryKey(commodity));
            if(commodity != null){
                commodityCache.set(commodityId,commodity);
            }
        }
        return commodity;
    }

    public List<CommodityDto> listCommodity(List<Long> ids){
        List<CommodityDto> commodities = commodityCache.mutGet(ids);
        int index = 0 ;
        for (CommodityDto commodity:commodities ){
            if(commodity == null){
                commodity = parse(commodityDao.selectByPrimaryKey(ids.get(index)));
                commodityCache.set(ids.get(index),commodity);
                index ++ ;
            }
        }
        return commodities;
    }

    public void updateSalesStatus(Long commodityId,Integer status){
        Long currTime  = System.currentTimeMillis();
        commodityDao.updateSaleStatus(commodityId,status,currTime);
        commodityCache.drop(commodityId);
    }
    private CommodityDto parse(Commodity commodity){
        CommodityDto dto = CommodityDto.from(commodity);
        return dto;
    }
}
