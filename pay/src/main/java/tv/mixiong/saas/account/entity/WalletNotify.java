package tv.mixiong.saas.account.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WalletNotify {

    private WalletNotifyType type;
    private float money;
    private String[] args;
    private String passport;

    public WalletNotify(String passport, int type, float money, String... args) {
        this.passport = passport;
        this.type = WalletNotifyType.get(type);
        this.money = money;
        this.args = args;
    }

    public String getPassport() {
        return passport;
    }

    public int getType() {
        return type.getType();
    }

    private SimpleDateFormat format = new SimpleDateFormat("MM月dd日");

    public String getMessage() {
        if (StringUtils.isBlank(type.getMessage())) {
            return "";
        }
        switch (type.getType()) {
            case 0:
            case 2:
            case 7: {
                String format = type.getMessage();
                String pattern = "{0,number,0.00}";
                String moneyStr = MessageFormat.format(pattern, money / 100);
                int inputLength = args.length;
                String[] formatInput = new String[inputLength + 1];
                System.arraycopy(args, 0, formatInput, 1, inputLength);
                formatInput[0] = moneyStr;
                return MessageFormat.format(format, formatInput);
            }
            case 4: {
                String date = format.format(new Date());
                String pattern = "{0,number,0.00}";
                String moneyStr = MessageFormat.format(pattern, money / 100);
                int inputLength = args.length - 2;
                String[] formatInput = new String[inputLength + 2];
                System.arraycopy(args, 0, formatInput, 1, inputLength);
                formatInput[0] = date;
                formatInput[inputLength] = moneyStr;
                formatInput[inputLength + 1] = args[inputLength - 1];
                return MessageFormat.format(type.getMessage(), formatInput);
            }
            case 1:
            case 3:
            case 5:
            case 6:
            case 8:
            case 18:
            default: {
                String date = format.format(new Date());
                String pattern = "{0,number,0.00}";
                String moneyStr = MessageFormat.format(pattern, money / 100);
                int inputLength = args.length;
                String[] formatInput = new String[inputLength + 2];
                System.arraycopy(args, 0, formatInput, 1, inputLength);
                formatInput[0] = date;
                formatInput[inputLength] = moneyStr;
                formatInput[inputLength + 1] = args[inputLength - 1];
                return MessageFormat.format(type.getMessage(), formatInput);
            }
        }

    }

    public String getJSON() {
        switch (type.getType()) {
            case 0:
            case 2:
            case 7: {
                return null;
            }
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            default: {
                String pattern = "{0,number,0.00}";
                String moneyStr = MessageFormat.format(pattern, money / 100);
                JSONObject result = new JSONObject();
                JSONArray data = new JSONArray();
                switch (type.getType()) {
                    case 1: {
                        result.put("first", "您收到一笔课程收入");
                        addJSONParam(data, "key", "收入金额", "value", moneyStr + args[3], "is_money", "true");
                        addJSONParam(data, "key", "课程标题", "value", args[0]);
                        addJSONParam(data, "key", "支付用户", "value", args[1] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark","收入金额已经计入您的米熊账户中，请您在账户明细中查看详细收支情况。");
                        break;
                    }
                    case 3: {
                        result.put("first", "您收到一笔课程退款");
                        addJSONParam(data, "key", "退款金额", "value", moneyStr + args[3], "is_money", "true");
                        addJSONParam(data, "key", "课程标题", "value", args[0]);
                        addJSONParam(data, "key", "退款老师", "value", args[1] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark","退款金额用已经原路退回给您的购买时使用的支付账户，请您注意查收。");
                        break;
                    }
                    case 4: {
                        result.put("first", "您有一笔课程返利收入");
                        addJSONParam(data, "key", "收入金额", "value", moneyStr + args[1], "is_money", "true");
                        addJSONParam(data, "key", "课程标题", "value", args[0]);
                        addJSONParam(data, "key", "课程老师", "value", args[3] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark","返利收入已经发放至您的米币账户，请您注意查收。");
                        break;
                    }
                    case 5: {

                        result.put("first", "您支出了一笔课程退款");
                        addJSONParam(data, "key", "支出金额", "value", moneyStr + args[3], "is_money", "true");
                        addJSONParam(data, "key", "课程标题", "value", args[0]);
                        addJSONParam(data, "key", "退款用户", "value", args[1] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark","退款金额用已经原路退回给购买用户，请您注意账户余额变化。");
                        break;
                    }
                    case 8: {
                        result.put("first", "您收到一笔课程退款");
                        addJSONParam(data, "key", "退款金额", "value", moneyStr + args[3], "is_money", "true");
                        addJSONParam(data, "key", "课程标题", "value", args[0]);
                        addJSONParam(data, "key", "退款老师", "value", args[1] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark","");
                        break;
                    }
                    case 13: {
                        result.put("first", "您收到一笔频道订阅收入");
                        addJSONParam(data, "key", "收入金额", "value", moneyStr + args[3], "is_money", "true");
                        addJSONParam(data, "key", "频道名称", "value", args[0]);
                        addJSONParam(data, "key", "订阅用户", "value", args[1] + "（米号：" + args[2] + "）");
                        addJSONParam(data, "key", "订阅时长", "value", args[4]);
                        result.put("data", data);
                        result.put("remark","收入金额已经计入您的米熊账户中，请您注意查收。");
                        break;
                    }
                    case 14: {
                        result.put("first", "您有一笔会员分销佣金收入");
                        addJSONParam(data, "key", "收入金额", "value", moneyStr + args[1], "is_money", "true");
                        addJSONParam(data, "key", "受邀用户", "value", args[3] + "（米号：" + args[2] + "）");
                        result.put("data", data);
                        result.put("remark", "分销收入已经计入您的米熊账户中，七天后自动解冻并可提现。");
                        break;
                    }
                    case 15: {
                        result.put("first", "您本有一笔会员分销佣金收入，可惜您不再是米熊会员，无法获得");
                        addJSONParam(data, "key", "收入金额", "value", moneyStr + args[1], "is_money", "true");
                        addJSONParam(data, "key", "受邀用户", "value", args[3]);
                        result.put("data", data);
                        result.put("remark", "不用担心，只要您续费米熊会员，依然可以获得新的佣金收入。");
                        break;
                    }
                    case 16: {
                        result.put("first", "您通过邀请好友开通米熊会员获得一笔现金奖励");
                        addJSONParam(data, "key", "奖励金额", "value", moneyStr + args[1], "is_money", "true");
                        addJSONParam(data, "key", "受邀用户", "value", args[3]);
                        result.put("data", data);
                        result.put("remark", "这笔奖励已经计入您的米熊账户中，七天后自动解冻并可提现。");
                        break;
                    }
                }
                return result.toJSONString();

            }
        }

    }

    private void addJSONParam(JSONArray data, String... args) {
        JSONObject tmp = new JSONObject();
        for (int i = 0; i < args.length / 2; i++) {
            tmp.put(args[2 * i], args[2 * i + 1]);
        }
        data.add(tmp);
    }

    public String[] getArgs() {
        return args;
    }

    public float getMoney() {
        return money;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("WalletNotify[type=").append(type.getType()).append(",money=").append(money).append(",passport=")
                .append(passport).append(",message=").append(getMessage());
        return sb.toString();
    }
}


enum WalletNotifyType {
    DEFAULT(0, "账户发生金额变动，金额 {0} 元"),
    INCOME(1, "{0}\n\n您收到一笔课程收入\n课程标题：{1}\n支付用户：{2}（米号：{3}）\n收入金额：{4}{5}"),
    REFUND_BUYER(3, "{0}\n\n您收到一笔课程退款\n课程标题：{1}\n退款老师：{2}（米号：{3}）\n退款金额：{4}{5}"),
    SHARE_SHARER(4, "{0}\n\n您有一笔课程返利收入\n课程标题：{1}\n收入金额：{2}{3}"),
    REFUND_SELLER(5, "{0}\n\n您支出了一笔课程退款\n课程标题：{1}\n退款用户：{2}（米号：{3}）\n退款金额：{4}{5}"),
    SHARE_SELLER(6, "{0}\n\n您支出了一笔用户返利\n课程标题：{1}\n返利用户：{2}（米号：{3}）\n支出金额：{4}{5}"),
    SPREAD_SELLER(7, "金额：{0} 元 \n课程：{1}"),
    REFUND_BUYER_MONEY(8, "{0}\n\n您收到一笔课程退款\n课程标题：{1}\n退款老师：{2}（米号：{3}）\n退款金额：{4}{5}"),
    REWARD(2, "金额：{0} 元 \n课程：{1}"),
    INCOME_CONVERSACTION_UNFROZEN(9, ""),
    REFUND_CONVERSACTION_BUYER(10, ""),
    REFUND_CONVERSACTION_SELLER(11, ""),
    INCOME_CONVERSACTION_SELLER(12, ""),
    CHANNEL_SELLER(13, ""),
    VIP_SHARER(14, ""),
    VIP_MOCK_SHARER(15, ""),
    VIP_SHARE_FIXED_AMOUNT(16, ""),
    COUPON_SELLER(18, "{0}\n\n您支出了一笔优惠券抵扣\n课程标题：{1}\n抵扣用户：{2}（米号：{3}）\n支出金额：{4}{5}");


    private int type;
    private String message;

    WalletNotifyType(int type, String message) {
        this.type = type;
        this.message = message;
    }

    static HashMap<Integer, WalletNotifyType> types;

    static {
        types = new HashMap<Integer, WalletNotifyType>();
        for (WalletNotifyType one : WalletNotifyType.values()) {
            types.put(one.getType(), one);
        }
    }

    public int getType() {
        return type;
    }

    public static WalletNotifyType get(int intType) {
        if (types.containsKey(intType)) {
            return types.get(intType);
        } else {
            return DEFAULT;
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }


}

