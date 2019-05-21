package tv.mixiong.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 媒体类型枚举
 *
 */
public enum MediaFormatEnum {

    MP4(1, "mp4"),
    FLV(2, "flv");
    private int format;
    private String desc;

    MediaFormatEnum(int format, String desc) {
        this.format = format;
        this.desc = desc;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    static Map<String, MediaFormatEnum> valueMap = Maps.newHashMap();

    static {
        for (final MediaFormatEnum formatEnum : MediaFormatEnum.values()) {
            valueMap.put(formatEnum.desc, formatEnum);
        }
    }
    public static MediaFormatEnum getByCode(String type) {
        return valueMap.get(type);
    }
}
