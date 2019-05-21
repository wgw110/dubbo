package tv.mixiong.web;

import lombok.Data;

/**
 */
@Data
public class ServerException extends RuntimeException {
    private int  status;
    public ServerException(int status) {
        this.status = status;
    }

    public ServerException(int status, Exception e) {
        super(e);
        this.status = status;
    }

    public ServerException(int status, String e) {
        super(e);
        this.status = status;
    }

    public ServerException(int status, Throwable e) {
        super(e);
        this.status = status;
    }

    public ServerException(String message) {
        super(message);
        this.status = 50000;
    }

    public ServerException() {
        super("系统内部错误");
        this.status = 50000;
    }
}
