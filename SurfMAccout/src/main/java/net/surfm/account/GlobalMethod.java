package net.surfm.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import net.surfm.account.aop.AuthorizationFilter;
import net.surfm.account.aop.SessionListener;
import net.surfm.account.dto.LoginResult;
import net.surfm.account.dto.LoginType;
import net.surfm.account.exception.ApiKeyFailException;
import net.surfm.account.model.Account;

public class GlobalMethod {

	public static void checkApiKeyOk(HttpServletRequest req) {
		Object o = req.getAttribute(AuthorizationFilter.KEY_TOKEN_FAIL);
		if (o != null && o instanceof ApiKeyFailException) {
			ApiKeyFailException ae = (ApiKeyFailException) o;
			throw ae;
		}
	}

	public static LoginResult setupLogin(Account ud, HttpServletRequest req) {
		login(ud);
		LoginResult ans = new LoginResult();
		RequestAttributes ra = RequestContextHolder.currentRequestAttributes();
		ans.setjSessionId(ra.getSessionId());
		ans.setUsername(ud.getUsername());
		ans.setSteamId(ud.getSteamId());
		ans.setFbId(ud.getFbId());
		ans.setAccountType(ud.getType());
		ans.setUid(ud.getUid());
		ans.setCreateAt(ud.getCreateAt());
		HttpSession hs = SessionListener.getHttpSession(ra.getSessionId());
		if (hs == null) {
			hs = req.getSession();
			SessionListener.addHttpSession(hs);
		}
		hs.setAttribute("username", ud.getUsername());

		return ans;
	}

	public static void login(Account ud) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(ud, ud.getPassword(),
				ud.getAuthorities());
		Account o = (Account) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	
	public static void setupUid(LoginType lt,Account u, String uid) {
		switch (lt) {
		case STEAM:
			u.setSteamId(Integer.parseInt(uid));
			return ;
		case FB:
			u.setFbId(uid);
			return ;
		}
		throw new NullPointerException("not support ="+lt);
		
	}

	public static boolean isEmptyForAccount(LoginType lt,Account a) {
		switch (lt) {
		case STEAM:
			return  a.getSteamId() <= 0;
		case FB:
			return StringUtils.isBlank(a.getFbId());
		}
		throw new NullPointerException("not support ="+lt);
	}

}
