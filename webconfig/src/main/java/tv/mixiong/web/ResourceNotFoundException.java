package tv.mixiong.web;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }
    public ResourceNotFoundException(String e) {
        super(e);
    }

    public ResourceNotFoundException(Exception e) {
        super(e);
    }
}
