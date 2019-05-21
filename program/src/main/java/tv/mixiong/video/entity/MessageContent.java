package tv.mixiong.video.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息内容
 */
public class MessageContent {

    @JSONField(name = "c")
    private String content;

    @JSONField(name = "ext")
    private String ext;

    @JSONField(name = "tp")
    private Integer type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
