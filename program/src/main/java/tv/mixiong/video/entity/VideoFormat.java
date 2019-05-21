package tv.mixiong.video.entity;

/**
 * 视频url表
 */
public class VideoFormat {

    private Long id;

    private Long videoId; // 视频id

    private Long liveId; // 直播id

    /**
     * 格式， 0: ["", "原始"], 1: ["带水印", "原始"], 10: ["手机", "mp4"],
     * 20: ["标清", "mp4"], 30: ["高清", "mp4"], 210: ["手机", "hls"],
     * 220: ["标清", "hls"], 230: ["高清", "hls"]
     */
    private Integer definition = 0;

    private String url;// url 地址

    private Integer vbitrate = 0;// 码率

    private int vheight;// 高度 单位px

    private int vwidth;// 宽度 单位px

    private int mediaFormat;// 媒介类型

    private Long objId;

    private int type;

    private Long size;

    private Long updateTime;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getDefinition() {
        return definition;
    }

    public void setDefinition(Integer definition) {
        this.definition = definition;
    }

    public Integer getVbitrate() {
        return vbitrate;
    }

    public void setVbitrate(Integer vbitrate) {
        this.vbitrate = vbitrate;
    }

    public int getVheight() {
        return vheight;
    }

    public void setVheight(int vheight) {
        this.vheight = vheight;
    }

    public int getVwidth() {
        return vwidth;
    }

    public void setVwidth(int vwidth) {
        this.vwidth = vwidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }

    public int getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(int mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
