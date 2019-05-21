package tv.mixiong.saas.commodity.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
public class DiscountHistory {
    private long id;
    private int commodityId;
    private String passport;
    private Date updateTime;
    private int status;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
