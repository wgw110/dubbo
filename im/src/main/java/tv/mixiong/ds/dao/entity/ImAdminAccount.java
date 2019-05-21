package tv.mixiong.ds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * im 签名对象
 * im user sign
 * Created by scooler on 16/8/5.
 */
@Entity
@Table(name = "mx_im_admin_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImAdminAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "im_admin")
    private String imAdmin;

    @Column(name = "sdk_app_id")
    private String sdkAppId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "c_time")
    private Long crateTime;

    @Column(name = "u_time")
    private Long updateTime;
}
