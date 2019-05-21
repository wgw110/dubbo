package tv.mixiong.saas.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "wallet")
@Entity
public class Wallet {
	
	private Long id;
	/**
	 * 用户passport 或者时school
	 */
	private String identity;

	private Integer amount;

	private Integer balance;

	private Integer type;

	private String sn;

	private String title;

	private Long updateTime;

	private Long commodityId;

	private Integer sid;
}
