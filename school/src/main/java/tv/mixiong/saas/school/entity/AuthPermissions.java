package tv.mixiong.saas.school.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "permission")
@Entity
public class AuthPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent")
    private String parent;

    @Column(name = "permission")
    private String permission;

    @Column(name = "description")
    private String description;

    @Column(name = "list_order")
    private Integer listOrder;
}
