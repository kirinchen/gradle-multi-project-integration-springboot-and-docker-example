package net.surfm.account.sdk;

import org.springframework.http.HttpEntity;

public class DevResult<T> extends HttpEntity<T> {

	private T body;

	public DevResult(T t) {
		body = t;
	}

	@Override
	public T getBody() {
		return body;
	}

}
