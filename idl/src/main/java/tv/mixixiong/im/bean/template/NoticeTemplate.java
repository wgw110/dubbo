package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeTemplate extends BaseTemplate implements Serializable {

    @JSONField(name = "notice")
    private Notice notice;

    @JSONField(name = "type")
    private int type = 3;

    public static NoticeTemplate from (TemplateCommonFields commonFields, Notice content) {
        if (content == null) {
            return null;
        }
        NoticeTemplate noticeTemplate = new NoticeTemplate();
        noticeTemplate.setNotice(content);
        noticeTemplate.setCommonFields(commonFields);
        return noticeTemplate;
    }
}
