package tv.mixiong.saas.transaction.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus {

    CREATE(1, "已经创建,未支付"),
    PAYED(2, "已经支付"),
    CANCELED(3, "已经取消"),
    REMOVED(4, "太长时间未支付,订单超时,系统移除"),
    CREATE_REFUND(5,"已经创建，未退款"),
    REFUNDED(6,"退款完成");

    TransactionStatus(int code, String name) {
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

    static Map<Integer, TransactionStatus> allMap = new HashMap<Integer, TransactionStatus>();

    static {
        for (final TransactionStatus status : EnumSet.allOf(TransactionStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static TransactionStatus getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
