package net.surfm.account.sdk;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import net.surfm.account.dto.UserObjDto;
import net.surfm.infrastructure.JsonMap;

@Service
@Profile("accountSdkDev")
public class ItemAPIDevImpl implements UserObjAPIRest {

	@Override
	public HttpEntity<UserObjDto> create(String itemUid, JsonMap i, int amount, String ownerUid) {
		// TODO Auto-generated method stub
		return null;
	}



}
