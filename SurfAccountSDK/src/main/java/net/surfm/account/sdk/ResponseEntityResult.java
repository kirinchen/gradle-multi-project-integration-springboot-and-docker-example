package net.surfm.account.sdk;

import org.springframework.http.ResponseEntity;

public class ResponseEntityResult<T> implements Result<T> {

	private ResponseEntity<T> entity;

	public ResponseEntityResult(ResponseEntity<T> entity) {
		super();
		this.entity = entity;
	}

	@Override
	public T getBody() {
		return entity.getBody();
	}

}
