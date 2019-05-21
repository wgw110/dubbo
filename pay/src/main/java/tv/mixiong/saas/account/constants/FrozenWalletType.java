package tv.mixiong.saas.account.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FrozenWalletType {

    FROZEN(0,"冻结"),
    UNFROZEN_COMPUTER(1, "自动解冻"),
    UNFROZEN_HUMAN(2, "手动解冻");

    FrozenWalletType(int code, String name) {
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

    static Map<Integer, FrozenWalletType> allMap = new HashMap<Integer, FrozenWalletType>();

    static {
        for (final FrozenWalletType status : EnumSet.allOf(FrozenWalletType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static FrozenWalletType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
