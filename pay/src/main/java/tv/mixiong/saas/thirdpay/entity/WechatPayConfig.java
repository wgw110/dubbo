package tv.mixiong.saas.thirdpay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "wechat_pay_config")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatPayConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolId;

    private String appId;

    private String mchId;

    private Long createTime;

    private Long updateTime;
}
