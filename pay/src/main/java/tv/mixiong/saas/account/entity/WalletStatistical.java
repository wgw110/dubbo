package tv.mixiong.saas.account.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.mixiong.saas.account.dto.WalletCountDto;

/**
 * 用作钱包统计使用
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletStatistical {

    private Long id ;

    private String passport;

    private Integer income;

    private Integer outcome;

    private Long time;

    private Integer type;

    @JSONField(name = "program_count")
    private Integer programCount;

    @JSONField(name = "buyer_count")
    private Integer buyerCount;

    @JSONField(name = "net_income")
    private Integer netIncome;

    public static WalletStatistical from (WalletCountDto walletDto, String passport, Long time , int type){
        return WalletStatistical.builder().income(walletDto.getTotalIncome()).passport(passport)
                .outcome(walletDto.getTotalOutcome()).type(type).time(time).buyerCount(walletDto.getBuyerCount())
                .netIncome(walletDto.getTotalIncome() + walletDto.getTotalOutcome()).programCount(walletDto.getProgramCount()).build();
    }
}
