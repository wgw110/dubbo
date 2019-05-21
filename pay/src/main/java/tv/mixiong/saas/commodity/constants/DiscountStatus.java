package tv.mixiong.saas.commodity.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mazhiyu on 16/7/2.
 */
public enum DiscountStatus {

    START(1, "开始"),
    IN(2, "折扣中"),
    END(3,"结束");

    DiscountStatus(int code, String name) {
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

    static Map<Integer, DiscountStatus> allMap = new HashMap<Integer, DiscountStatus>();

    static {
        for (final DiscountStatus status : EnumSet.allOf(DiscountStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static DiscountStatus getByCode(Integer type) {
        return allMap.get(type);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
