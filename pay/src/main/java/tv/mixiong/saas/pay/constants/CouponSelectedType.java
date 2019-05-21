package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CouponSelectedType {

    DEFAULT(0, "默认状态"),
    SELECTED(1, "选中状态");


    CouponSelectedType(int code, String name) {
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

    static Map<Integer, CouponSelectedType> allMap = new HashMap<Integer, CouponSelectedType>();

    static {
        for (final CouponSelectedType status : EnumSet.allOf(CouponSelectedType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static CouponSelectedType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
