package tv.mixixiong.im.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushInfo implements Serializable {

    @JSONField(name = "action_url")
    private String alertActionUrl;

    @JSONField(name = "content")
    private String alertContent;

    @JSONField(name = "title")
    private String alertTitle;

    @JSONField(name = "payload")
    private String payload;


    @Override
    public String toString() {
        return "PushInfo{" +
                "alertActionUrl='" + alertActionUrl + '\'' +
                ", alertContent='" + alertContent + '\'' +
                ", alertTitle='" + alertTitle + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }

    public static PushInfo from(String title, String content, String actionUrl, String payload) {
        PushInfo pushInfo = new PushInfo();
        pushInfo.setAlertActionUrl(actionUrl);
        pushInfo.setAlertContent(content);
        pushInfo.setAlertTitle(title);
        pushInfo.setPayload(payload);
        return pushInfo;
    }

    public static PushInfo from(String title, String content, String actionUrl) {
        PushInfo pushInfo = new PushInfo();
        pushInfo.setAlertActionUrl(actionUrl);
        pushInfo.setAlertContent(content);
        pushInfo.setAlertTitle(title);
        return pushInfo;
    }
}
