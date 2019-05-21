package tv.mixiong.dto;

import lombok.Data;
import mobi.mixiong.util.BeanUtils;
import tv.mixiong.entity.Commodity;

@Data
public class CommodityDto {
    private Long id;

    private String name;

    private Integer plat;

    private Integer price;

    private Integer originPrice;

    private Integer marketPrice;

    private Long schoolId;

    private Integer commodityType;

    private Integer marketType;

    private Long attachId;

    private Integer onSale;

    private Long createTime;

    private Long updateTime;

    private Long startSaleTime;

    private Long endSaleTime;

    private Integer vipDiscountRate;

    public static CommodityDto from(Commodity commodity){
        CommodityDto dto = new CommodityDto();
        BeanUtils.copyProperties(commodity,dto);
        return dto;
    }
}
