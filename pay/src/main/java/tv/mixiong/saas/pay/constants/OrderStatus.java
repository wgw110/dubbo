package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 */
public enum OrderStatus {

    CREATE(1, "已经创建,未支付"),
    PROCESSING(2,"验证中"),
    PAYED(3, "支付成功"),
    CANCELED(4, "已经取消"),
    REMOVED(5, "太长时间未支付,订单超时,系统移除"),
    LOCKED(6,"订单正在处理中,稍后"),//每次callback的时候主动对订单上锁,防止两次给用户权益
    REFUND(7,"已退款"),
    ;

    OrderStatus(int code, String name) {
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

    static Map<Integer, OrderStatus> allMap = new HashMap<Integer, OrderStatus>();

    static {
        for (final OrderStatus status : EnumSet.allOf(OrderStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static OrderStatus getByCode(Integer type) {
        return allMap.get(type);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
