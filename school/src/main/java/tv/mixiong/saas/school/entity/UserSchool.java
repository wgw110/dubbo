package tv.mixiong.saas.school.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "user_school")
@Entity
public class UserSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_master")
    private Integer isMaster;

    @Column(name = "purview")
    private String purview;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;
}
