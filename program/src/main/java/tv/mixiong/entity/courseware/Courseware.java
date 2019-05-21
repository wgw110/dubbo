package tv.mixiong.entity.courseware;

import lombok.Data;


@Data
public class Courseware {

    private Long id;
    private Long liveId; // 场次id
    private Integer idx; // 顺序
    private Long createTime;
    private Long updateTime;
    private String url;
    private String encUrl;
    private String snapshotUrl;
}
