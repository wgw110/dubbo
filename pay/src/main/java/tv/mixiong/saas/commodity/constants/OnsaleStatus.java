package tv.mixiong.saas.commodity.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 */
public enum OnsaleStatus {

    DEFAULT(0, "DEFAULT"),
    ON_SALE(1, "正在售卖"),
    STOP_SALE(2, "停止售卖"),
    ON_SALE_NO_FROZEN(3, "实时售卖,不冻结"),
    WAIT_SAIL(4,"等待售卖");

    OnsaleStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    static Map<Integer, OnsaleStatus> allMap = new HashMap<Integer, OnsaleStatus>();

    static {
        for (final OnsaleStatus status : EnumSet.allOf(OnsaleStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static OnsaleStatus getByCode(Integer type) {
        return allMap.get(type);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static boolean isOnsale(int code){
        return code == OnsaleStatus.ON_SALE.getCode() || code == OnsaleStatus.ON_SALE_NO_FROZEN.getCode();
    }
}
