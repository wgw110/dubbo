package tv.mixiong.xinge;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


/**
 * .
 */
public class PushParam {

    @JSONField(name = "action_url")
    private String actionUrl;

    @JSONField(serialize = false)
    private String title;

    @JSONField(serialize = false)
    private String content;

    @JSONField(serialize = false)
    private int env;

    @JSONField(serialize = false)
    private String token;

    @JSONField(serialize = false)
    private String account;

    @JSONField(serialize = false)
    private String tag;

    @JSONField(serialize = false)
    private int type;

    @JSONField(serialize = false)
    private String passport;

    @JSONField(serialize = false)
    private String sendTime;


    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
