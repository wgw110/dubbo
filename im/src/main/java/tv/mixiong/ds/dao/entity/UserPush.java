package tv.mixiong.ds.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * .
 */
@Entity
@Table(name = "mx_user_push")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPush {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token")
    private String token;

    @Column(name = "passport")
    private String passport;

    @Column(name = "platform")
    private int platform;

    @Column(name = "status")
    private int status;

    @Column(name = "u_time")
    private long updateTime;

    @Column(name = "voip_token")
    private String voipToken;

    @Column(name = "product_id")
    private Integer productId;
}
