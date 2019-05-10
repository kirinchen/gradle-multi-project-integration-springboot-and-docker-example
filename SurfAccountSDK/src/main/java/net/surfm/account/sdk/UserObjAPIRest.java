package net.surfm.account.sdk;

import org.springframework.http.HttpEntity;

import net.surfm.account.dto.UserObjDto;
import net.surfm.infrastructure.JsonMap;

public interface UserObjAPIRest {

	public HttpEntity<UserObjDto> create(  String itemUid, JsonMap i, int amount, String ownerUid);
	

}
