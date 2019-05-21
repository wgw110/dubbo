package tv.mixiong.xinge;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * .
 */
public class PushResponse {
    @JSONField(name = "push_id")
    private String pushId;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
