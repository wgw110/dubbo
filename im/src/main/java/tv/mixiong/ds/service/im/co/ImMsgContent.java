package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.annotation.JSONField;


public class ImMsgContent {
    @JSONField(name = "Data")
    private String data;

    @JSONField(name = "Text")
    private String text;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
