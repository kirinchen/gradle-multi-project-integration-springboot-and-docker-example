package net.surfm.account.exception;

import net.surfm.exception.SurfmRuntimeException;

public class ApiKeyFailException extends SurfmRuntimeException {

	public ApiKeyFailException() {
		super();
	}

	public ApiKeyFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiKeyFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiKeyFailException(String message) {
		super(message);
	}

	public ApiKeyFailException(Throwable cause) {
		super(cause);
	}
	
	

}
