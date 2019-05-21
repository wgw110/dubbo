package tv.mixiong.entity.live;

import lombok.Data;
import tv.mixiong.enums.VideoStatus;

@Data
public class Live {
    private Long id;
    private String passport;
    private String subject;
    private Integer playerLayout;
    private Long createTime;
    private Long startTime;
    private Long planStartTime;
    private Integer status;
    private Integer uploadStatus;
    private Integer programIndex;
    private Integer videoStatus = VideoStatus.NO_SAVE.value();
    private Integer relive = 2;
    private Long programId;
    private Integer recordType;
    private Integer playtime;
    private Integer liveType;
    private Long finishTime;
    private int onTime;
    private Long resourceId;
    private Long endTime;

}
