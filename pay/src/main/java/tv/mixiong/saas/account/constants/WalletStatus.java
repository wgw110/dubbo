package tv.mixiong.saas.account.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum WalletStatus {

    DEFAULT(0,"默认"),
    FROZON(1, "冻结");

    WalletStatus(int code, String name) {
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

    static Map<Integer, WalletStatus> allMap = new HashMap<Integer, WalletStatus>();

    static {
        for (final WalletStatus status : EnumSet.allOf(WalletStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static WalletStatus getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
