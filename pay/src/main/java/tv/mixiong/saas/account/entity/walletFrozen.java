package tv.mixiong.saas.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wallet_frozen")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class walletFrozen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identity;

    private Long updateTime;

    private Integer amount;

    private Integer frozenBalance;

    private Long attachId;

    private Integer type;

    private Long sid;
}
