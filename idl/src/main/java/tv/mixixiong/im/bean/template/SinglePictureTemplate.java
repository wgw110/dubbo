package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinglePictureTemplate extends BaseTemplate implements Serializable {

    @JSONField(name = "single_picture")
    private Picture picture;

    @JSONField(name = "type")
    private int type = 1;

    public static SinglePictureTemplate from (TemplateCommonFields commonFields, Picture picture) {
        SinglePictureTemplate template = new SinglePictureTemplate();
        template.setPicture(picture);
        template.setCommonFields(commonFields);
        template.setType(1);
        return template;
    }
}
