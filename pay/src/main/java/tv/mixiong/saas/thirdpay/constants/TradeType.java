package tv.mixiong.saas.thirdpay.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

public enum TradeType {

    JS(1,"JSAPI"),
    NATIVE(2,"Native"),
    APP(3,"app"),
    MWEB(4,"H5");

    private static Map<Integer,TradeType> allMap;

    static {
        allMap = Maps.newHashMap();
        for (final TradeType tradeType:EnumSet.allOf(TradeType.class)){
            allMap.put(tradeType.getType(),tradeType);
        }
    }

    public static TradeType valueOf(int type){
        return allMap.get(type);
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    TradeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;

    private String desc;


}
