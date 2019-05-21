package tv.mixiong.web;

public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String e) {
        super(e);
    }

    public AuthException(Exception e) {
        super(e);
    }
}
