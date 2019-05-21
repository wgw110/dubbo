package tv.mixiong.saas.school.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nation")
    private String nation;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "nick")
    private String nick;

    @Column(name = "create_time")
    private Long createTime;

}
