package tv.mixiong.saas.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passport;

    private String orderSn;

    private Long commodityId;

    private Integer amount;

    private Integer totalMoney;

    private Long createTime;

    private Long updateTime;

    private Long expireTime;

    private Integer orderStatus;

    private Integer plat;

    private String ip;

    private String memo;

    private String serviceCallback;

    private String couponSn;
}
