package tv.mixiong.saas.account.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum WalletType {

    OUTCOME(0, "支出"),
    INCOME(1, "收入"),
    TRANSFER(2, "提现"),
    REFUND(3, "退款"),//老师退款给用户
    @Deprecated
    GROUP_INCOME(4, "群收入"),//已经弃用
    SHARE_OUTCOME(5, "分享返现"),//老数据，包含主播返现支出，用户返现收入
    @Deprecated
    SPREAD_INCOME(6, "推广收入"),
    @Deprecated
    AGENT_INCOME(7, "代理收入"),
    @Deprecated
    SPREAD_OUTCOME(8, "推广支出"),
    DIVIDE_OUTCOME(9, "分成支出"),//老数据，包含班课分成支出，签约分成支出
    @Deprecated
    COUPON_OUTCOME(10, "优惠券支出"),//暂无
    VIP_INCOME(11, "会员收入"),
    @Deprecated
    VIP_DIVIDE_INCOME(12, "会员分成收入"),//会员片库主播分成收入
    @Deprecated
    VIP_DIVIDE_OUTCOME(13, "会员分成支出"),
    INCOME_FIX(14, "收入补偿"),
    WRONG_DATA(15, "异常数据"),
    INNER_TRANSFER_OUTCOME(16, "余额转出"),
    INNER_TRANSFER_INCOME(17, "余额转入"),
    @Deprecated
    VIP_INVITE_INCOME(18, "会员分销收入"),
    @Deprecated
    VIP_INVITE_OUTCOME(19, "会员分销支出"),
    @Deprecated
    SPREAD_PROGRAM_INCOME(20, "课程推广收入"),
    @Deprecated
    SPREAD_PROGRAM_OUTCOME(21, "课程推广支出"),
    DIVIDE_SIGN_OUTCOME(22, "签约分成"),
    DIVIDE_CLASS_OUTCOME(23, "班课分成"),
    DIVIDE_FEE_OUTCOME(24, "服务器带宽费用"),
    @Deprecated
    VIP_SHARE_INCOME(25, "邀请开通会员收入"),
    @Deprecated
    VIP_SHARE_OUTCOME(26, "邀请开通会员支出"),
    RED_PACKAGE_INCOME(27, "红包暂存收入"),
    RED_PACKAGE_OUTCOME(28, "红包暂存支出"),
    RED_PACKAGE_REWARD(29, "红包奖学金"),
    RED_PACKAGE_REMAIN_REFUND(30, "红包奖学金余额退款"),
    TEACHER_COUPON_OUTCOME(31, "老师优惠券支出"),
    SYSTEM_VIP_OUTCOME(32, "会员成本支出"),
    SYSTEM_COUPON_OUTCOME(33, "平台优惠券成本支出"),
    TEACHER_REFUND_COUPON(34, "老师优惠券退款"),
    @Deprecated
    TEACHER_REFUND_SHARD(35, "老师分享返利退款"),
    SYSTEM_REFUND_COUPON(36, "平台系统优惠券退款"),
    SYSTEM_REFUND_VIP_DISCOUNT(37, "系统vip优惠退款"),
    REFUND_TO_USER(38, "退款"),//用户收到退款
    SYSTEM_DISCOUNT_OUTCOME(39, "普通用户平台折扣支出"),
    SYSTEM_REFUND_DISCOUNT(40, "普通用户平台折扣退款"),
    CLAIMCODE_DISCOUNT_OUTCOME(41, "券码折扣支出"),
    CLAIMCODE_REFUND_DISCOUNT(42, "券码折扣退款"),
    COMPANY_TRANSFER_FAIL(43, "对公提现失败");

    WalletType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    static Map<Integer, WalletType> allMap = new HashMap<Integer, WalletType>();

    static {
        for (final WalletType status : EnumSet.allOf(WalletType.class)) {
            allMap.put(status.getCode(), status);
        }
    }

    public static WalletType getByCode(Integer type) {
        return allMap.get(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
