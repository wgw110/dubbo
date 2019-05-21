package tv.mixiong.saas.commodity.cache;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Component;
import tv.mixiong.dto.CommodityDto;
@Component
public class CommodityCache extends BaseValueCache<Long,CommodityDto> {
    @Override
    protected String getPrefix() {
        return "mx:saas:pay:commodity:";
    }
}
