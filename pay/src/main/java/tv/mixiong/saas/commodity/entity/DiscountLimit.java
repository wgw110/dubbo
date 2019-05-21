package tv.mixiong.saas.commodity.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
public class DiscountLimit {
    private long id;
    private String name;
    private int commodityId;
    private int totalAmount;
    private int usedAmount;
    private Date createTime;
    private float discountRate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
