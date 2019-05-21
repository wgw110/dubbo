package tv.mixiong.saas.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
@ExcelSheet(name = "收入明细")
public class WalletDetailIncomeVo {

    @ExcelField(name = "序号")
    private Integer rownum;

    @ExcelField(name = "付款日期")
    private String updateTimeStr;

    @ExcelField(name = "付款类型")
    @JSONField(name = "type_desc")
    private String typeDesc;


    @ExcelField(name = "用户米号")
    @JSONField(name = "passport")
    private String passport;

    @ExcelField(name = "用户昵称")
    @JSONField(name = "nick_name")
    private String nickName;

    @ExcelField(name = "商品名称")
    @JSONField(name = "commodity_name")
    private String commodityName;

    @ExcelField(name = "收益金额")
    private Double realMoney;

    public static WalletDetailIncomeVo from(WalletDetailVo vo){
        WalletDetailIncomeVo result = new WalletDetailIncomeVo();
        BeanUtils.copyProperties(vo,result);
        result.setRealMoney( 1.0 * Math.abs(vo.getAmount()) / 100);
        result.setUpdateTimeStr(DateUtil.dateFormat(vo.getUpdateTime(),DateUtil.YYYYMMDDHHMM));
        return result;
    }
}
