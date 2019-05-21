package tv.mixiong.ds.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mx_im_user_sign")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImUserSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_sign")
    private String userSign;

    @Column(name = "sdk_app_id")
    private String sdkAppId;

    @Column(name = "expire_in_s")
    private Integer expireInS; // 超时时间,单位秒

    @Column(name = "sign_at")
    private Integer signAt; // 签名时间戳,单位秒

    @Column(name = "expire_time")
    private Integer expireTime;// 超时时间戳,单位秒

    @Column(name = "status")
    private Integer status;// 状态

}
