package tv.mixiong.saas.commodity.constants;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public enum CommodityMarketTypeEnum {
    COMMON(1, "普通商品"),
    BARGAIN(2, "砍价商品"),
    TUAN(4, "拼团商品");

    CommodityMarketTypeEnum(int status, String text) {
        this.status = status;
        this.text = text;
    }

    private int status;
    private String text;

    public int getStatus() {
        return status;
    }


    static Map<Integer, CommodityMarketTypeEnum> allMap = new HashMap<Integer, CommodityMarketTypeEnum>();

    static {
        for (final CommodityMarketTypeEnum status : EnumSet.allOf(CommodityMarketTypeEnum.class)) {
            allMap.put(status.getStatus(), status);
        }
    }

    public static CommodityMarketTypeEnum getByCode(Integer type) {
        return allMap.get(type);
    }

    /**
     * 判断是否是普通商品
     * @param type
     * @return
     */
    public static boolean isCommonCommodity(int type){
        return type == COMMON.status;
    }

}
