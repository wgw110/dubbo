package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CouponStatus {
    CREATE(1, "创建成功"),
    NOTINUSE(2, "当前不可用"),
    UNUSE(3,"使用期内未使用"),
    USED(4, "已使用"),
    DELETE(5,"已删除"),
	EXPIRED(6,"已过期"),
	LOCKED(7,"锁定");
	CouponStatus(int code, String name) {
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

    static Map<Integer, CouponStatus> allMap = new HashMap<Integer, CouponStatus>();

    static {
        for (final CouponStatus status : EnumSet.allOf(CouponStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static CouponStatus getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
