package tv.mixiong.saas.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletIndexVo {

    @JSONField(name = "today_income")
    private Integer todayIncome;

    @JSONField(name = "today_outcome")
    private Integer todayOutcome;

    @JSONField(name = "mouth_income")
    private Integer mouthIncome;

    @JSONField(name = "mouth_outcome")
    private Integer mouthOutcome;

    @JSONField(name = "total_income")
    private Integer totalIncome;

    @JSONField(name = "total_outcome")
    private Integer totalOutcome;

    @JSONField(name = "today_net_income")
    private Integer todayNetIncome;

    @JSONField(name = "total_net_income")
    private Integer totalNetIncome;

    @JSONField(name = "balance")
    private Integer balance;

    @JSONField(name = "today_transfer_amount")
    private Integer todayTransferAmount;

    @JSONField(name = "mouth_transfer_amount")
    private Integer mouthTransferAmount;

    @JSONField(name = "total_transfer_amount")
    private Integer totalTransferAmount;

    @JSONField(name = "curr_frozen_amount")
    private Integer currFrozenAmount;

}
