package tv.mixiong.saas.commodity.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CommodityType {


    MiCoin(3,"米币"),
    PROPS(4,"道具") ,
    SERIES_LIVE(8,"系列课"),
    MX_MEMBER(15,"会员"),
    VIDEO(16,"视频课");

    CommodityType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    static Map<Integer, CommodityType> allMap = new HashMap<Integer, CommodityType>();

    static {
        for (final CommodityType commodityType : EnumSet.allOf(CommodityType.class)) {
            allMap.put(commodityType.getType(), commodityType);
        }
    }

    public static CommodityType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static boolean isProgramType(CommodityType type){
        return type.equals(CommodityType.SERIES_LIVE) || type.equals(CommodityType.VIDEO);
    }
}
