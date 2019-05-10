package net.surfm.exception;

public class SurfmRuntimeException extends RuntimeException {

	public SurfmRuntimeException() {
		super();
	}

	public SurfmRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SurfmRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SurfmRuntimeException(String message) {
		super(message);
	}

	public SurfmRuntimeException(Throwable cause) {
		super(cause);
	}

}