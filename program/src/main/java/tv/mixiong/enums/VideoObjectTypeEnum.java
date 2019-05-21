package tv.mixiong.enums;

public enum VideoObjectTypeEnum {
    PROGRAM_LIVE(1, "课程", 0),
    VIDEO_RESOURCE(2, "视频素材", 1),
    PROGRAM_PREVIEW(3, "预告", 3),
    VA_ANSWER(4, "视答", 1),
    VIDEO_RESOURCE_LIBRARY(5, "视频库素材", 2),
    SNS_FORUM(6, "米圈", 2);
    private int type;
    private String desc;
    private int vodType; // 在vod系统中的type


    VideoObjectTypeEnum(int type, String desc, int vodType) {
        this.type = type;
        this.desc = desc;
        this.vodType = vodType;
    }

    public int type() {
        return this.type;
    }

    public int getVodType() {
        return vodType;
    }

    public void setVodType(int vodType) {
        this.vodType = vodType;
    }
}
