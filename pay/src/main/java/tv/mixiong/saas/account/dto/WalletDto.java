package tv.mixiong.saas.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Integer rownum;
    private int amount;
    private int type;
    private String transaction_sn;
    private String title;
    private Date updateTime;
}
