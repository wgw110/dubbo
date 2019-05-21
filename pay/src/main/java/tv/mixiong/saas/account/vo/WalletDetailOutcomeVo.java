package tv.mixiong.saas.account.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mobi.mixiong.util.BeanUtils;
import mobi.mixiong.util.DateUtil;
import mobi.mixiong.util.excel.ExcelField;
import mobi.mixiong.util.excel.ExcelSheet;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelSheet(name = "支出明细")
public class WalletDetailOutcomeVo {

    @ExcelField(name = "序号")
    private Integer rownum;

    @ExcelField(name = "支出日期")
    private String updateTimeStr;

    @ExcelField(name = "支出类型")
    private String typeDesc;

    @ExcelField(name = "详情描述")
    private String detail;

    @ExcelField(name = "用户米号")
    private String passport;

    @ExcelField(name = "用户昵称")
    private String nickName;

    @ExcelField(name = "商品名称")
    private String commodityName;

    @ExcelField(name = "支出金额")
    private Double realMoney;


    public static WalletDetailOutcomeVo from(WalletDetailVo vo){
        WalletDetailOutcomeVo result = new WalletDetailOutcomeVo();
        BeanUtils.copyProperties(vo,result);
        result.setRealMoney( 1.0 * Math.abs(vo.getAmount()) / 100);
        result.setUpdateTimeStr(DateUtil.dateFormat(vo.getUpdateTime(),DateUtil.YYYYMMDDHHMM));
        return result;
    }

}
