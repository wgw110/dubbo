package tv.mixiong.saas.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDetailVo {
    private Integer rownum;

    @JSONField(name = "update_time")
    private Long updateTime;

    @JSONField(name = "type_desc")
    private String typeDesc;

    @JSONField(name = "detail")
    private String detail;

    @JSONField(name = "passport")
    private String passport;

    @JSONField(name = "nick_name")
    private String nickName;

    @JSONField(name = "commodity_name")
    private String commodityName;

    @JSONField(name = "amount")
    private Integer amount;

    @JSONField(name = "type")
    private Integer type;

    public void setAmount(Integer amount) {
        this.amount = Math.abs(amount);
    }
}
