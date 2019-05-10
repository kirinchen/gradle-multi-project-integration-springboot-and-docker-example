package net.surfm.account.api;

import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.surfm.account.GlobalMethod;
import net.surfm.account.aop.CurrentUser;
import net.surfm.account.dto.ExLoginFormDto;
import net.surfm.account.dto.LoginResult;
import net.surfm.account.dto.LoginType;
import net.surfm.account.dto.UserLoginFormDto;
import net.surfm.account.dto.UserSignupFormDto;
import net.surfm.account.model.Account;
import net.surfm.account.service.AccountService;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.VerifyRecaptcha;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
@Validated
@Controller
@RequestMapping(value = "/api/v1/public/")
public class PublicController {

//	JSONException j;
	@Inject
	private ObjectMapper jsonMapper;
	@Inject	
	private AccountService accountService;
	@Inject
	private ObjectMapper objectMapper;
	@Value("#{new Boolean('${recaptcha.enable}')}")
	private boolean recaptchaEnable;


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	LoginResult signUp(@Valid @RequestBody UserSignupFormDto userDto,HttpServletRequest req) {
		if(recaptchaEnable) VerifyRecaptcha.check(userDto);
		Account ud = accountService.signup(userDto);
		return GlobalMethod.setupLogin(ud, req);
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "type=REST_API")
	LoginResult loginForRest(@Valid @RequestBody UserLoginFormDto userDto,HttpServletRequest req) {
		if(recaptchaEnable) VerifyRecaptcha.check(userDto);
		Account ud = accountService.getAndMerge(userDto);
		LoginResult lr = GlobalMethod.setupLogin(ud, req);
		return lr;
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "type=FB_API")
	LoginResult loginOrSignForFB(@Valid @RequestBody ExLoginFormDto userDto,HttpServletRequest req) {
		return loginOrSignForEx(LoginType.FB,userDto,req);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "type=STEAM_API")
	LoginResult loginOrSignForSteam(@Valid @RequestBody ExLoginFormDto userDto,HttpServletRequest req) {
		return loginOrSignForEx(LoginType.STEAM,userDto,req);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	Map test(HttpServletRequest req) throws  ClassNotFoundException, IOException {
		HashMap<String, Object> ans = new HashMap<>();
		ans.put("test", "xxxx");
		ans.put("test1", Long.class);
		ans.put("test2", (long)123);
		HashMap<String, Object> intest = new HashMap<>();
		intest.put("ww", "xxx");
		ans.put("test3", intest);
		ans.put("test4", (long)123);
		
		String s = objectMapper.writeValueAsString(ans);
		HashMap mt = objectMapper.readValue(s, HashMap.class);
		
		ans.put("testintest", mt.get("test3").getClass());
		Class<?> classType = Class.forName("java.lang.Integer");
		
		Object out= classType.cast(mt.get("test2"));
		ans.put("test2", out);
		
		return ans;
	}	
	
	
	LoginResult loginOrSignForEx(LoginType t, ExLoginFormDto userDto,HttpServletRequest req) {
		Account ud = accountService.findOrCreateBySteam(t,userDto.getUid());
		LoginResult lr = GlobalMethod.setupLogin(ud, req);
		return lr;
	}

	
	@ResponseStatus(OK)
	@ResponseBody
	@RequestMapping(value = "/logined", method = RequestMethod.GET)
	public boolean logined(@CurrentUser Account activeUser) throws JsonProcessingException {
		String jsonInString = jsonMapper.writeValueAsString(activeUser);
		CommUtils.pl(jsonInString);
		return true;
	}	



}
