package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class VideoResultInfo {

    @JSONField(name = "duration")
    private Integer duration;

    @JSONField(name = "width")
    private Integer width;

    @JSONField(name = "height")
    private Integer height;

    @JSONField(name = "mp4")
    private List<VideoInfo> mp4List;
    
    @JSONField(name = "flv")
    private List<VideoInfo> flvList;
    
    @JSONField(name = "download")
    private DownloadVideoInfo downloadVideoInfo;

    @JSONField(name = "sdownload")
    private DownloadVideoInfo sdownloadVideoInfo;

}
