package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 */
public class VideoUrlVo {

    private Integer vbitrate;

    private Integer definition;

    private Integer vheight;

    private Integer vwidth;

    private String url;

    @JSONField(name = "record_media_type")
    private Integer recordMediaType;

    private Long size;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getVbitrate() {
        return vbitrate;
    }

    public void setVbitrate(Integer vbitrate) {
        this.vbitrate = vbitrate;
    }

    public Integer getDefinition() {
        return definition;
    }

    public void setDefinition(Integer definition) {
        this.definition = definition;
    }

    public Integer getVheight() {
        return vheight;
    }

    public void setVheight(Integer vheight) {
        this.vheight = vheight;
    }

    public Integer getVwidth() {
        return vwidth;
    }

    public void setVwidth(Integer vwidth) {
        this.vwidth = vwidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRecordMediaType() {
        return recordMediaType;
    }

    public void setRecordMediaType(Integer recordMediaType) {
        this.recordMediaType = recordMediaType;
    }
}
