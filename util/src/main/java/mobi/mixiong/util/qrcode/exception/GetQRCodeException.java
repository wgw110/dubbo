package mobi.mixiong.util.qrcode.exception;

/**
 */
public class GetQRCodeException extends Exception{
    public GetQRCodeException(String msg) {
        super(msg);
    }
    public GetQRCodeException(Exception e) {
        super(e);
    }
    public GetQRCodeException(Throwable e) {
        super(e);
    }
    public GetQRCodeException(String msg, Exception e) {
        super(msg, e);
    }
}
