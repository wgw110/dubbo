package tv.mixixiong.im.response;

import lombok.Data;

import java.io.Serializable;

/**
 * User: scooler Date: 16-05-20
 */
@Data
public class CommonResponse implements Serializable {

    private int status = ResponseStatus.SUCCESS_STATUS.getStatus();

    private String statusText = ResponseStatus.SUCCESS_STATUS.getDesc();

    private String data;

    public CommonResponse() {
    }

    /**
     * 默认成功的请求
     */
    public static CommonResponse DEFAULT_SUCCESS = new CommonResponse();

    public CommonResponse(int status, String t) {
        this.status = status;
        this.data = t;
    }

    public CommonResponse(ResponseStatus status, String text) {
        this.status = status.getStatus();
        this.statusText = text == null ? status.getDesc() : text;
    }
}
