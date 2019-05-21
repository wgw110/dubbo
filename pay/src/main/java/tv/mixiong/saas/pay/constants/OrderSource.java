package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 */
public enum OrderSource {

    NORMAL(0, "默认商品订单"),
    CART_ORDER(1,"购物车订单"),
    CART_SPLIT_ORDER(2, "购物车拆分订单");

    OrderSource(int code, String name) {
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

    static Map<Integer, OrderSource> allMap = new HashMap<Integer, OrderSource>();

    static {
        for (final OrderSource status : EnumSet.allOf(OrderSource.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static OrderSource getByCode(Integer type) {
        return allMap.get(type);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
