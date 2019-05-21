package tv.mixiong.ds.service.im.co;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


public class OfflinePushInfo {
    @JSONField(name = "PushFlag")
    private int pushFlag;

    @JSONField(name = "Desc")
    private String desc;

    @JSONField(name = "Ext")
    private String ext;

    @JSONField(name = "Sound")
    private String sound;

    @JSONField(name = "AndroidInfo")
    private JSONObject androidInfo;

    public int getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(int pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public JSONObject getAndroidInfo() {
        return androidInfo;
    }

    public void setAndroidInfo(JSONObject androidInfo) {
        this.androidInfo = androidInfo;
    }
}
