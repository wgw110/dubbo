package tv.mixixiong.im.response;

import java.io.Serializable;

public enum ResponseStatus implements Serializable {
    //200是成功的代码
    SUCCESS_STATUS(200, "OK"),
    SERVICE_UNAVAILABLE(50000, "系统内部错误");
    private int status;
    private String desc;

    ResponseStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
