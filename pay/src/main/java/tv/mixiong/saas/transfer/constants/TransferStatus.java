package tv.mixiong.saas.transfer.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransferStatus {

    CREATE(1, "发起转账"),
    TRANSFERED(2, "转账成功"),
    CANCELED(3, "转账已取消"),
    REMOVED(4, "转账已经移除");

    TransferStatus(int code, String name) {
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

    static Map<Integer, TransferStatus> allMap = new HashMap<Integer, TransferStatus>();

    static {
        for (final TransferStatus status : EnumSet.allOf(TransferStatus.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static TransferStatus getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
