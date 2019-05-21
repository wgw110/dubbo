package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePictureTemplate extends BaseTemplate implements Serializable {

    @JSONField(name = "multiple_pictures")
    private List<Picture> pictures;


    @JSONField(name = "type")
    private int type = 2;

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static MultiplePictureTemplate from (TemplateCommonFields commonFields, List<Picture> pictures) {
        if (pictures == null || pictures.isEmpty()) {
            return null;
        }
        MultiplePictureTemplate template = new MultiplePictureTemplate();
        template.setPictures(pictures);
        template.setCommonFields(commonFields);
        template.setType(2);
        return template;
    }
}
