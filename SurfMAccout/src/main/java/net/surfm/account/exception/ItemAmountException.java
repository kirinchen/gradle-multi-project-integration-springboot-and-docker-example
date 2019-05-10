package net.surfm.account.exception;

import net.surfm.exception.SurfmRuntimeException;

public class ItemAmountException extends SurfmRuntimeException {

	public ItemAmountException() {
		super();
	}

	public ItemAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ItemAmountException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItemAmountException(String message) {
		super(message);
	}

	public ItemAmountException(Throwable cause) {
		super(cause);
	}

}
