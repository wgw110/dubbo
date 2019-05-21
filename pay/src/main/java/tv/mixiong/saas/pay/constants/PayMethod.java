package tv.mixiong.saas.pay.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 */
public enum PayMethod {

    WECHAT_APP("wechat_app", "微信支付", 1),
    WECHAT_WAP("wechat_wap", "微信支付", 2),
    WECHAT_JS("wechat_js", "微信支付", 6),
    WECHAT_QR("wechat_qr", "微信支付", 3),
    WECHAT_APPLET("wechat_applet", "微信小程序支付", 7),
    ALIPAY_APP("alipay_app", "支付宝支付", 5),
    ALIPAY_JS("alipay_js", "支付宝支付", 4),
    IAP("iap", "苹果内购", 0),
    MI_COIN("mi_coin", "米粒支付", 0),//仅留作钱包展示用
    MI_COIN_2("mi_coin_2", "米粒支付", 0),//仅留作钱包展示用
    MIBI("mibi", "米币支付", 0),
    FREE_PAY("free_pay", "免费支付", 0);


    PayMethod(String name, String desc, int defaultTradeType) {
        this.name = name;
        this.desc = desc;
        this.defaultTradeType = defaultTradeType;
    }

    private String name;
    private String desc;
    private Integer defaultTradeType;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getDefaultTradeType() {
        return defaultTradeType;
    }

    static Map<String, PayMethod> allMap = new HashMap<String, PayMethod>();

    static {
        for (final PayMethod payMethod : EnumSet.allOf(PayMethod.class)) {
            allMap.put(payMethod.getName(), payMethod);
        }
    }

    public static boolean isThirdPay(PayMethod payMethod) {
        return payMethod == PayMethod.WECHAT_APP || payMethod == PayMethod.WECHAT_WAP ||
                payMethod == PayMethod.WECHAT_JS || payMethod == PayMethod.WECHAT_QR ||
                payMethod == PayMethod.WECHAT_APPLET || payMethod == PayMethod.ALIPAY_APP ||
                payMethod == PayMethod.ALIPAY_JS;
    }

    public static PayMethod getPayMethodByName(String payMethod) {
        return allMap.get(payMethod);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
