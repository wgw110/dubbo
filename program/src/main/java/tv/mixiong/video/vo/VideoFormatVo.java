package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tv.mixiong.video.entity.VideoFormat;

/**
 */
@Data
public class VideoFormatVo{
    @JSONField(name = "url")
    private String url;

    @JSONField(name = "encUrl")
    private String encUrl;

    @JSONField(name = "definition")
    private Integer definition;

    @JSONField(name = "bitrate")
    private Integer vbitrate;

    @JSONField(name = "height")
    private int vheight;

    @JSONField(name = "width")
    private int vwidth;

    @JSONField(name = "media_format")
    private int mediaFormat;

    @JSONField(name = "obj_id")
    private Long objId;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "size")
    private Long size;

    @JSONField(name = "md5")
    private String md5;

    @JSONField(name = "k")
    private String encKey;

    @JSONField(name = "l")
    private String dataLength;

    @JSONField(name = "m")
    private String marsKey;

    public static VideoFormatVo from(VideoFormat videoFormat){
        if (videoFormat == null) {
            return null;
        }
        VideoFormatVo videoFormatVo = new VideoFormatVo();
        videoFormatVo.setDefinition(videoFormat.getDefinition());
        videoFormatVo.setUrl(videoFormat.getUrl());
        videoFormatVo.setVbitrate(videoFormat.getVbitrate());
        videoFormatVo.setVheight(videoFormat.getVheight());
        videoFormatVo.setVwidth(videoFormat.getVwidth());
        videoFormatVo.setMediaFormat(videoFormat.getMediaFormat());
        videoFormatVo.setType(videoFormat.getType());
        videoFormatVo.setObjId(videoFormat.getObjId());
        videoFormatVo.setSize(videoFormat.getSize());
        return videoFormatVo;
    }
}
