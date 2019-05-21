package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 视频文件vo
 */
public class VideoFileVo {

    private String vid;

    private Integer duration;

    private String fileName;

    @JSONField(name = "image_url")
    private String imageUrl;

    private String fileId;

    private Integer status;

    private List<VideoUrlVo> playSet;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<VideoUrlVo> getPlaySet() {
        return playSet;
    }

    public void setPlaySet(List<VideoUrlVo> playSet) {
        this.playSet = playSet;
    }
}
