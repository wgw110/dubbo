package mobi.mixiong.exception;

public class HttpException extends Exception {
	
	private static final long serialVersionUID = -4707210535385221192L;

	public HttpException() {
	}

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(String msg, Throwable e) {
		super(msg, e);
	}

	public HttpException(Throwable e) {
		super(e);
	}
}