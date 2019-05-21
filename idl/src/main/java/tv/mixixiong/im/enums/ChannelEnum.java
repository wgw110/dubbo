package tv.mixixiong.im.enums;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * ChannelEnum
 * *
 */
public enum ChannelEnum implements Serializable {
    XG_PUSH(1, "信鸽push", 1),
    IM_MSG(2, "im消息", 2),
    WX_MSG(3, "微信消息", 4),
    SMS(4, "短信", 8),
    IM_GROUP_MSG(5, "群消息", 16),
    IOS_VOIP(6, "苹果callkit", 32),
    IM_TEXT_MSG(7, "IM普通文本消息", 64),
    IM_C2C_MSG(8, "IMc2c消息", 128),

    XG_PUSH_V1(11, "信鸽push", 2 << 7),
    IM_MSG_V1(21, "im消息", 2 << 8),
    WX_MSG_V1(31, "微信消息", 2 << 9),
    SMS_V1(41, "短信", 2 << 10),
    IM_GROUP_MSG_V1(51, "群消息", 2 << 11),
    IOS_VOIP_V1(61, "苹果callkit", 2 << 12),
    IM_TEXT_MSG_V1(71, "IM普通文本消息", 2 << 13),
    IM_C2C_MSG_V1(81, "IMc2c消息", 2 << 14),
    ;

    ChannelEnum(int type, String desc, int bit) {
        this.type = type;
        this.desc = desc;
        this.bit = bit;
    }

    private int type;
    private String desc;
    private int bit;

    public static List<ChannelEnum> valueOf(int bit) {
        List<ChannelEnum> channels = Lists.newArrayList();
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            if ((channelEnum.getBit() & bit) > 0) {
                channels.add(channelEnum);
            }
        }
        return channels;
    }

    public int getBit() {
        return bit;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
