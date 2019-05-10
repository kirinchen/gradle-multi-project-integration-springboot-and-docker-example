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

import net.surfm.account.dto.LoginResult;
import net.surfm.account.dto.UserSignupFormDto;

@Service
public class PublicRest {

	@Qualifier("Account_SDK")
	@Inject
	private RestTemplate rest;
	@Value("${apikey}")
	private String apikey;
	@Value("${accountSdkHost}")
	private String accountSdkHost;

	public  HttpEntity< LoginResult> signUp(UserSignupFormDto userDto) {
		String urlTemp = "${url}/api/v1/public/signup";
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", accountSdkHost);
		String formattedString = StrSubstitutor.replace(urlTemp, data);
		System.out.println(formattedString);
		return ( rest.postForEntity(formattedString,  userDto, LoginResult.class));
	}

}
