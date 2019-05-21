package tv.mixiong.video.entity;

import lombok.Data;

/**
 * 回放信息表
 */
@Data
public class Video {

    private Long id;
    private Long liveId; // 直播id
    private Long objId;
    private String vid;
    private String fileId;
    private Integer duration;// 视频持续时间,单位:秒
    private String fileName;// 视频文件名
    private String coverUrl;// 视频封面图片
    private int type;
    private int createType;

    /**
     * 视频状态， -1：未上传完成，不存在；0：初始化，暂未使用；1：审核不通过，暂未使用；
     * 2：正常；3：暂停；4：转码中；5：发布中；6：删除中；7：转码失败；10：等待转码；
     * 11：转码部分完成（终态）100：已删除
     */
    private Integer status;

    private Integer orderIdx = 1;// 序号

    private String downloadUrl;

    private Long downloadSize;

    private String downloadMd5;

    private String sdownloadUrl;

    private Long sdownloadSize;

    private String sdownloadMd5;

    private Integer dataLength;

    private String encKey;

    private String marsKey;

}
