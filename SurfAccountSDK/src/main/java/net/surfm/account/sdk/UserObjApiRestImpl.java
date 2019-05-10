package net.surfm.account.sdk;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.surfm.account.dto.UserObjDto;
import net.surfm.infrastructure.JsonMap;

@Service
@Profile("accountSdkRestFul")
public class UserObjApiRestImpl implements UserObjAPIRest {

	@Qualifier("Account_SDK")
	@Inject
	private RestTemplate rest;
	@Value("${apikey}")
	private String apikey;
	@Value("${accountSdkHost}")
	private String accountSdkHost;
	
	@Override
	public HttpEntity<UserObjDto> create(String itemUid, JsonMap i, int amount, String ownerUid) {
		String urlTemp = "${url}/api/v1/userobj/${ownerUid}/create/${itemUid}/?amount=${amount}";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		data.put("ownerUid", ownerUid);
		data.put("itemUid", itemUid);
		data.put("amount", amount+"");
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return  rest.postForEntity(formattedString,  i, UserObjDto.class);
	}





	
	
}
