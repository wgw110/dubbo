package tv.mixiong.saas.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderSn;

    private String transactionSn;

    private String thirdSn;

    /**
     * 进行支付的pay_config_id
     */
    private Long payConfigId;

    private Integer transactionType;

    private Long createTime;

    private Long expireTime;

    /**
     * 用来标识退款或转账完成时间
     */
    private Long endTime;

    private Long updateTime;

    private Integer transactionStatus;

    private Integer totalMoney;

    private Integer realMoney;

    private Integer plat;

    private String identity;

    private Long commodityId;

    private String callback;

    private Integer tradeType;

    private String memo;

}
