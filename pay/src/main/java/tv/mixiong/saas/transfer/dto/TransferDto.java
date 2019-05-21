package tv.mixiong.saas.transfer.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TransferDto {

    private String passport;
    @JSONField(name = "transfer_type")

    private String transferType;

    private Integer amount;

    @JSONField(name = "real_amount")
    private Integer realAmount;

    private Integer tax;

    @JSONField(name = "copyright_amount")
    private Integer copyrightAmount;

    @JSONField(name = "percent_amount")
    private Integer percentAmount;

    @JSONField(name = "transfer_status")
    private Integer transferStatus;

    @JSONField(name = "create_time")
    private Long createTime;
}
