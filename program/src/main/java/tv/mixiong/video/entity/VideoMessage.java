package tv.mixiong.video.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class VideoMessage {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "room_id")
    private Long roomId;

    @JSONField(name = "ts")
    private Long ts;// 时间戳

    @JSONField(name = "from")
    private String from;// 信息来源

    @JSONField(name = "cnt")
    private MessageContent content;


}
