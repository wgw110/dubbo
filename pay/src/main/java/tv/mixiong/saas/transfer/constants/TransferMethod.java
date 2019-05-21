package tv.mixiong.saas.transfer.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransferMethod {
    WECHAT_APP("wechat_app", "微信支付", 1),
    WECHAT_JS("wechat_js", "微信支付", 2),
    ALIPAY_APP("alipay_app", "支付宝支付", 4),
    TOBUSINESS("tobusiness", "对公付款", 0),
    INNER_TRANSFER("inner_transfer", "内部转账", 0);

    TransferMethod(String name, String desc, int tradeType) {
        this.name = name;
        this.desc = desc;
        this.tradeType = tradeType;
    }

    private String name;
    private String desc;
    private int tradeType;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getTradeType() {
        return tradeType;
    }

    static Map<String, TransferMethod> allMap = new HashMap<String, TransferMethod>();

    static {
        for (final TransferMethod transferMethod : EnumSet.allOf(TransferMethod.class)) {
            allMap.put(transferMethod.getName(), transferMethod);
        }
    }

    public static TransferMethod getTransferMethodByName(String payMethod) {
        return allMap.get(payMethod);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
