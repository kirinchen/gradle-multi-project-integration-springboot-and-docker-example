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

import net.surfm.account.dto.AppDto;
import net.surfm.infrastructure.OptionalDto;

@Service
public class AppAPIRest {

	@Qualifier("Account_SDK")
	@Inject
	private RestTemplate rest;	
	@Value("${accountSdkHost}")
	private String accountSdkHost;
	
	public HttpEntity<OptionalAppDto> findByName( String appName) {
		String urlTemp = "${url}/api/v1/app/${appName}";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		data.put("appName", appName);
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return ( rest.getForEntity(formattedString,OptionalAppDto.class ));
	}	
	
	public static class OptionalAppDto extends OptionalDto<AppDto>{}
	
	public HttpEntity<AppDto> create(String appName){
		String urlTemp = "${url}/api/v1/app/${appName}/create";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		data.put("appName", appName);
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return ( rest.postForEntity(formattedString,  null, AppDto.class));
	}
	
	
}
