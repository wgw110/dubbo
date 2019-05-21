package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class ImMessageBody {
    @JSONField(name = "MsgType")
    private String msgType;

    @JSONField(name = "MsgContent")
    private ImMsgContent msgContent;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public ImMsgContent getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(ImMsgContent msgContent) {
        this.msgContent = msgContent;
    }

    public static ImMessageBody from(TencentImMsg msg) {
        ImMessageBody body = new ImMessageBody();
        body.setMsgType("TIMCustomElem");
        ImMsgContent content = new ImMsgContent();
        JSONObject data = new JSONObject();
        data.put("userAction", msg.getUserAction());
        data.put("actionParam", msg.getActionParam());
        data.put("actionExt", msg.getActionExt());
        content.setData(JSON.toJSONString(data));
        body.setMsgContent(content);
        return body;
    }

    public static ImMessageBody fromText(String text) {
        ImMessageBody body = new ImMessageBody();
        body.setMsgType("TIMTextElem");
        ImMsgContent content = new ImMsgContent();
        content.setText(text);
        body.setMsgContent(content);
        return body;
    }
}
