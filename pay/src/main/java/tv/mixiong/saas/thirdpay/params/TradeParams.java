package tv.mixiong.saas.thirdpay.params;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TradeParams {

    /**
    * 商品名称
    * */
    private String name;

    /**
     * 商品描述
     * */
    private String description;

    /**
     * alipay单位是元，保留两位小数
     * wechat_pay单位是分
     */
    private long price;

    private String sn;

    private String ip;

    private Long expireTime;
    private Long createTime;

    private String notifyUrl;

    private String returnUrl;

    /**
     * 微信专用
     */
    private String openId;

    private Integer tradeType;

    private String schoolId;

}
