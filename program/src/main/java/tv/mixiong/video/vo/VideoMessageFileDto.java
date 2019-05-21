package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.beans.BeanUtils;
import tv.mixiong.video.entity.VideoMessageFile;


public class VideoMessageFileDto {

    @JSONField(serialize = false)
    private Long id;

    @JSONField(name = "file_url")
    private String fileName;

    @JSONField(serialize = false)
    private Long roomId;

    @JSONField(name = "create_time")
    private Long createTime;


    public static VideoMessageFileDto from (VideoMessageFile videoMessageFile) {
        if (videoMessageFile == null) {
            return null;
        }
        VideoMessageFileDto obj = new VideoMessageFileDto();
        BeanUtils.copyProperties(videoMessageFile, obj);
        return obj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
