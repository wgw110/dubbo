package tv.mixiong.saas.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletResp {
	private Date updateTime;
	private int income;
	private int outcome;
	private String title;
	private int series;
	private int onsale_series;
	//商品id
	private int attachid;
	private int commodityType;
	private int commodityId;
	private int programId;
	//课程名称
	private String subject;
}
