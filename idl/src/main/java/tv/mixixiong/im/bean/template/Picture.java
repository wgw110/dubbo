package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements Serializable {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "picture")
    private String picture;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "action_url")
    private String actionUrl;

    public static Picture from (String title, String pictureUrl, String desc, String actionUrl) {
        return new Picture(title, pictureUrl, desc, actionUrl);
    }
}
