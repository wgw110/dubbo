package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CouponUseType {

    ALL_NEW_USER(1, "全场通用新手券"),
    TEACHER(2, "老师通用券"),
    COMMODITY(3, "商品通用券"),
    ALL_SHARE(4,"全场通用分享券"),
    ALL_EDIT(5, "全场通用编辑券");

    CouponUseType(int code, String name) {
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

    static Map<Integer, CouponUseType> allMap = new HashMap<Integer, CouponUseType>();

    static {
        for (final CouponUseType status : EnumSet.allOf(CouponUseType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static CouponUseType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
