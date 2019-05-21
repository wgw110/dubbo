package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class VideoInfo {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "width")
    private Integer width;

    @JSONField(name = "height")
    private Integer height;

    @JSONField(name = "fsize")
    private Long fsize;

    @JSONField(name = "media_format")
    private int videoFormat;

    @JSONField(name = "duration")
    private Integer duration;

}
