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
public class MiBiRecordVo {

    @JSONField(name = "amount")
    public Double amount;
    @JSONField(name = "type")
    public Integer type;
    @JSONField(name = "order_sn")
    public String orderSn;
    @JSONField(name = "update_time")
    public Long updateTime;
    @JSONField(name = "passport")
    public String passport;
}
