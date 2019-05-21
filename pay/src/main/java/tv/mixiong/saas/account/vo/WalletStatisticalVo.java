package tv.mixiong.saas.account.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mobi.mixiong.util.BeanUtils;
import mobi.mixiong.util.DateUtil;
import mobi.mixiong.util.excel.ExcelField;
import mobi.mixiong.util.excel.ExcelSheet;
import tv.mixiong.saas.account.entity.WalletStatistical;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelSheet(name = "概要")
public class WalletStatisticalVo {

    @ExcelField(name = "日期")
    private String timeStr;

    @ExcelField(name = "收入")
    private Double income;

    @ExcelField(name = "支出")
    private Double outcome;

    @ExcelField(name = "净收入")
    private Double netIncome;

    @ExcelField(name = "购课量")
    private Integer programCount;

    @ExcelField(name = "购课人数")
    private Integer buyerCount;

    public static WalletStatisticalVo from(WalletStatistical param){
        WalletStatisticalVo vo = new WalletStatisticalVo();
        BeanUtils.copyProperties(param,vo);
        vo.setTimeStr(DateUtil.dateFormat(param.getTime(),DateUtil.YYYY年MM月DD日));
        vo.setIncome(1.0 * Math.abs(param.getIncome()) / 100);
        vo.setOutcome(1.0 * Math.abs(param.getOutcome()) / 100);
        vo.setNetIncome(1.0 * Math.abs(param.getNetIncome()) / 100);
        return vo;
    }

}
