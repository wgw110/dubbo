package tv.mixiong.saas.transfer.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransferType {

    COMPANY(1, "对公"),
    PERSON(2, "个体");

    TransferType(int code, String name) {
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

    static Map<Integer, TransferType> allMap = new HashMap<Integer, TransferType>();

    static {
        for (final TransferType status : EnumSet.allOf(TransferType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static TransferType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
