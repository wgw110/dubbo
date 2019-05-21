package tv.mixiong.saas.account.dto;

import lombok.Data;

/**
 * 钱包数量，包含收益和支出
 */
@Data
public class WalletCountDto {

    private String passport;

    private Integer totalIncome;

    private Integer totalOutcome;

    private Integer programCount;

    private Integer buyerCount;
}
