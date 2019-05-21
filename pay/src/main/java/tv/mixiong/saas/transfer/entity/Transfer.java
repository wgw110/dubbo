package tv.mixiong.saas.transfer.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer")
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transferSn;

    private String identity;

    private String transferAccount;
    /**
     * 发起申请的金额
     */
    private Integer amount;
    /**
     * 最后到账的金额
     */
    private Integer realAmount;

    private Integer tax;

    private Integer taxBefore;
    /**
     * 微信手续费
     */
    private Integer procedureAmount;

    private Integer transferStatus;

    private Long createTime;

    private Long endTime;

    private Long updateTime;

    private String thirdSn;
}
