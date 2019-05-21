package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class MxMessageV1Dto {
    @JSONField(name = "msg_type")
    private Integer msgType;

    @JSONField(name = "msg_content")
    private String msgContent;

    @JSONField(name = "msg_title")
    private String msgTitle;

    @JSONField(name = "program")
    private JSONObject program;

    @JSONField(name = "channel_messages")
    private JSONArray channelMessages;

    @JSONField(name = "action_url")
    private String actionUrl;

    @JSONField(name = "picture")
    private JSONObject picture;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public JSONObject getProgram() {
        return program;
    }

    public void setProgram(JSONObject program) {
        this.program = program;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public JSONArray getChannelMessages() {
        return channelMessages;
    }

    public void setChannelMessages(JSONArray channelMessages) {
        this.channelMessages = channelMessages;
    }

    public JSONObject getPicture() {
        return picture;
    }

    public void setPicture(JSONObject picture) {
        this.picture = picture;
    }
}
