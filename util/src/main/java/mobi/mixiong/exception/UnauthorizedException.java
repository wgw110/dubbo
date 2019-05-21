package mobi.mixiong.exception;

import mobi.mixiong.http.InfoCode;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -7415231656534380017L;

	private InfoCode infoCode;

	/**
	 * 默认返回服务错误
	 */
	public UnauthorizedException() {
		this.infoCode = InfoCode.OPERATION_NOT_ALLOWED;
	}

	public UnauthorizedException(String message) {
		super(message);
		this.infoCode = InfoCode.OPERATION_NOT_ALLOWED;
	}

	public UnauthorizedException(InfoCode infoCode) {
		this.infoCode = infoCode;
	}

	public UnauthorizedException(InfoCode infoCode, String message) {
		super(message);
		this.infoCode = infoCode;
	}

	public InfoCode getInfoCode() {
		return infoCode;
	}
}