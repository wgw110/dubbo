package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateCommonFields implements Serializable {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "send_time")
    private Long sendTime;

    @JSONField(name = "redirect_desc")
    private String redirectDesc;

    @JSONField(name = "action_url")
    private String actionUrl;

    @JSONField(name = "conversation_desc")
    private String conversationDesc;


    public static TemplateCommonFields genDefaultFields(String title, String actionUrl, String conversationDesc) {
        return TemplateCommonFields.builder()
                .redirectDesc("查看详情")
                .sendTime(System.currentTimeMillis())
                .title(title)
                .actionUrl(actionUrl)
                .conversationDesc(conversationDesc)
                .build();
    }

    public static TemplateCommonFields genDefaultFields(String title, String actionUrl, String conversationDesc, String redirectDesc) {
        return TemplateCommonFields.builder()
                .redirectDesc(redirectDesc)
                .sendTime(System.currentTimeMillis())
                .title(title)
                .actionUrl(actionUrl)
                .conversationDesc(conversationDesc)
                .build();
    }
}
