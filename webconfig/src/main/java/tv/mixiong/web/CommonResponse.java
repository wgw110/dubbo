package tv.mixiong.web;

import com.alibaba.fastjson.annotation.JSONField;

public class CommonResponse {

    @JSONField(name = "status")
    private int status = 200;

    @JSONField(name = "statusText")
    private String statusText = "success";

    @JSONField(name = "data")
    private Object data;

    public CommonResponse() {
    }

    /**
     * 默认成功的请求
     */
    public static CommonResponse DEFAULT_SUCCESS = new CommonResponse();

    public CommonResponse(Object t) {
        if (t instanceof CommonResponse) {
            CommonResponse response = (CommonResponse) t;
            this.status = response.getStatus();
            this.statusText = response.getStatusText();
            this.data = response.getData();
        } else {
            this.data = t;
        }
    }

    public CommonResponse(int status, String text) {
        this.status = status;
        this.statusText =  text;
    }

    public static CommonResponse error(String text) {
        CommonResponse response = new CommonResponse();
        response.setStatus(5000);
        response.setStatusText(text);
        return response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

}
