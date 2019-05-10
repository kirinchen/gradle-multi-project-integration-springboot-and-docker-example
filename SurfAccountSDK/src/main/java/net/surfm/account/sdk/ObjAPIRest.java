package net.surfm.account.sdk;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.surfm.account.dto.ObjDto;

@Service
public class ObjAPIRest {

	@Qualifier("Account_SDK")
	@Inject
	private RestTemplate rest;	
	@Value("${accountSdkHost}")
	private String accountSdkHost;
	
	public HttpEntity<ObjDto> create(String appUid, ObjDto dto) {
		String urlTemp = "${url}/api/v1/obj/app/${appUid}/";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		data.put("appUid", appUid);
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return ( rest.postForEntity(formattedString,  dto, ObjDto.class));
	}	
	
	public HttpEntity<ObjDto> findByUid( String uid) {
		String urlTemp = "${url}/api/v1/obj/${uid}/";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		data.put("uid", uid);
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return ( rest.getForEntity(formattedString,   ObjDto.class));
	}
	
}
