package tv.mixiong.video.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import lombok.Data;
import tv.mixiong.enums.CreateTypeEnum;
import tv.mixiong.enums.MediaFormatEnum;
import tv.mixiong.enums.VideoObjectTypeEnum;

import java.util.List;

@Data
public class VideoVo{
    @JSONField(name = "duration")
    private int duration;

    @JSONField(name = "formats")
    private List<VideoFormatVo> formats;

    @JSONField(name = "download_format")
    private VideoFormatVo downloadFormat;

    @JSONField(name = "s_format")
    private VideoFormatVo sFormat;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "c_type")
    private int createType;

    @JSONField(name = "cover_url")
    private String coverUrl;

    public static VideoVo from(JSONObject jsonObject){
        VideoVo videoVo = new VideoVo();
        videoVo.setDuration(jsonObject.getIntValue("duration"));
        videoVo.setCoverUrl(jsonObject.getString("image_url"));
        videoVo.setCreateType(CreateTypeEnum.USER.type());
        videoVo.setType(VideoObjectTypeEnum.SNS_FORUM.type());

        VideoFormatVo formatVo = new VideoFormatVo();
        formatVo.setUrl(jsonObject.getString("video_url"));
        formatVo.setSize(jsonObject.getLongValue("size"));
        formatVo.setVheight(jsonObject.getIntValue("width"));
        formatVo.setVwidth(jsonObject.getIntValue("height"));
        formatVo.setMediaFormat(MediaFormatEnum.MP4.getFormat());

        videoVo.setFormats(Lists.newArrayList(formatVo));
        return videoVo;
    }
}
