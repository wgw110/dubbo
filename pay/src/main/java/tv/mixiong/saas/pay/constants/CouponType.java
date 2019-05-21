package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CouponType {
    //删除折扣券
    Discount(1, "折扣券"),
    FullCut(2, "满减券"),
    Vouchers(3, "代金券"),
    //删除兑换券
    //Certificates(4, "兑换券")
    ;

    CouponType(int code, String name) {
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

    static Map<Integer, CouponType> allMap = new HashMap<Integer, CouponType>();

    static {
        for (final CouponType status : EnumSet.allOf(CouponType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static CouponType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
