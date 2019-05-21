package tv.mixiong.saas.pay.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mobi.mixiong.cache.BaseCo;
import tv.mixiong.saas.pay.constants.CouponType;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseCo {
    long id;
    String sn;

    String passport;
    int type;
    int ruleId;

    String name;
    String description;
    Date endTime;
    Date startTime;

    int status;
    String orderSn;
    Date useTime;
    Date createTime;
    Date updateTime;
    String creator;
    Integer receiveId;

    //关联属性
    String rule;
    int full;
    int cut;
    float discount;
    int useType;

    //计算属性
    int discoutPrice;//能够减免的价格
    int priceAfterDiscount;//减免后的最终价格

    //购物车推荐标记
    int recommendFlag;
    //订单页选中标记
    int selectedFlag;

    String supportTeacher;//平台优惠券支持老师

    List<String> unUseDesc;//不可用描述

    public Date getStartTime() {
        if (startTime == null) {
            return new Date();
        } else {
            return startTime;
        }
    }

    public String genUnUseDesc() {
        if (getUnUseDesc() != null && getUnUseDesc().size() >= 1) {
            if (getUnUseDesc().size() == 1) {
                return getUnUseDesc().get(0);
            } else {
                String desc = "";
                for (int i = 0; i < getUnUseDesc().size(); i++) {
                    desc += (i + 1) + ". " + getUnUseDesc().get(i) + "\n";
                }
                return desc;
            }
        } else {
            return null;
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public String getValue() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        switch (CouponType.getByCode(this.getType())) {
            case FullCut:
                return nf.format(this.getCut() / 100f);
            case Vouchers:
                return nf.format(this.getCut() / 100f);
            default:
                return "1";
        }
    }

    @JSONField(serialize = false, deserialize = false)
    public String getUnit() {
        switch (CouponType.getByCode(this.getType())) {
            case FullCut:
            case Vouchers:
                return "元";
            default:
                return "张";
        }
    }
}
