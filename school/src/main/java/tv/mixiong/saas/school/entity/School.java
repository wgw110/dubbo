package tv.mixiong.saas.school.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "school")
@Entity
/**
 * 与小鹅通的appId匹配
 * */
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "logo")
    private String logo;

    @Column(name = "slogan")
    private String slogan;

}
