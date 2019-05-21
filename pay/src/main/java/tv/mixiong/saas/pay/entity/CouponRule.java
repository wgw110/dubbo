package tv.mixiong.saas.pay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CouponRule {
    int id;
    String name;
    String description;
    int type;
    int useType;
    String rule;
    int full;
    int cut;
    float discount;
    Integer commodityId;
    Integer validityTime;
    Date createTime;
    Date updateTime;
    Date startTime;
    Date expireTime;
    Integer status;
    String creator;
}
