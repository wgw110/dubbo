package tv.mixiong.video.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class DownloadVideoInfo {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "size")
    private Long size;

    @JSONField(name = "md5")
    private String md5;


    @JSONField(name ="dataLength")
    Integer dataLength;

    @JSONField(name="encKey")
    String encKey;

    @JSONField(name="marsKey")
    String marsKey;
}
