package tv.mixiong.saas.account.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum  MiCoinType {

    RECHARGE(0,"充值"),
    PURCHASE(1, "购买商品"),
    REWARD_EXPENSE(3, "打赏支出"),
    REFUND_COMMON(4, "购买商品退款"),
    REFUND_MICOIN(5, "购买米粒退款"),
    SHARED(6,"分享返利返还");
    MiCoinType(int code, String name) {
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

    static Map<Integer, MiCoinType> allMap = new HashMap<Integer, MiCoinType>();

    static {
        for (final MiCoinType status : EnumSet.allOf(MiCoinType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static MiCoinType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
