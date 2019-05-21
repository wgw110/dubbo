package tv.mixiong.saas.pay.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Table(name = "order_pay_detail")
@Entity
public class OrderPayDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderSn;

    private String transactionSn;
    /**
     * 商品原价 （商品表的原价，不可修改）
     */
    private Integer originPrice;
    /**
     * 折扣（限时或者限人）
     */
    private Integer discount;
    /**
     * 商品现价
     */
    private Integer price;
    /**
     * 订单总价值
     */
    private Integer totalPrice;

    /**
     * 会员折扣多少钱
     */
    private Integer vipDiscount;

    private String couponSn;

    private Integer couponDiscount;

    private Integer priceAfterCouponDiscount;
    /**
     * 订单需要支付的金额
     */
    private Integer buyerAmount;
}
