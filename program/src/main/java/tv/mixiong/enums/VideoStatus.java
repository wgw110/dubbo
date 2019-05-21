package tv.mixiong.enums;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 录制视频状态
 */
public enum VideoStatus {

    SAVE(1, "保存回放且转码完成"),
    NO_SAVE(2, "不保存回放"),
    TRANSCODING(3, "保存回放,正在转码中"),
    DELETED(4, "删除");


    public static String getStatusText(int status) {
        switch (status) {
            case 1:
                return SAVE.getDesc();
            case 2:
                return NO_SAVE.getDesc();
            case 3:
                return TRANSCODING.getDesc();
            case 4:
                return DELETED.getDesc();
        }
        return null;
    }


    public static final Set<Integer> SAVE_STATUS_SET = Sets.newHashSet(SAVE.status, TRANSCODING.status);

    private Integer status;
    private String desc;

    public String getDesc() {
        return desc;
    }

    VideoStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer value() {
        return this.status;
    }


    public static boolean hasVideo(Integer videoStatus) {
        return SAVE.value().equals(videoStatus) || TRANSCODING.value().equals(videoStatus);
    }

    /**
     * @param videoStatus 视频状态
     * @return 是否保存成功
     */
    public static boolean isSaved(Integer videoStatus) {
        return SAVE.value().equals(videoStatus);
    }
}
