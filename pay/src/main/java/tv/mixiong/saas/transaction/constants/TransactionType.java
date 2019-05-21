package tv.mixiong.saas.transaction.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionType {

    PAY(1, "支付"),
    REFUND(2, "退款"),
    TRANSFER(3, "转账");

    TransactionType(int code, String name) {
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

    static Map<Integer, TransactionType> allMap = new HashMap<Integer, TransactionType>();

    static {
        for (final TransactionType type : EnumSet.allOf(TransactionType.class)) {
            allMap.put(type.getCode(), type);
        }
    }

    public static TransactionType getByCode(Integer type) {
        return allMap.get(type);
    }
}
