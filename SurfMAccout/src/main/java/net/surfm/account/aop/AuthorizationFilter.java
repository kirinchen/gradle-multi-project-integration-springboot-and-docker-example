package net.surfm.account.aop;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import net.surfm.account.GlobalMethod;
import net.surfm.account.dao.AccountDao;
import net.surfm.account.exception.ApiKeyFailException;
import net.surfm.account.model.Account;

@Component
public class AuthorizationFilter implements Filter {
	public final static String KEY_TOKEN_FAIL = "tokenException";
	private final static Logger LOG = Logger.getLogger(AuthorizationFilter.class.getName());

	@Inject
	private AccountDao accountDao;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		logRequest(hsr);
		setupApiKey(hsr);
		chain.doFilter(request, response);
	}

	private void setupApiKey(HttpServletRequest hsr) {
		String sid = hsr.getHeader("Authorization");
		if (StringUtils.isNotBlank(sid)) {
			HttpSession hs = SessionListener.getHttpSession(sid);
			if (hs != null) {
				loginBySession(hs);
			}else{
				hsr.setAttribute(KEY_TOKEN_FAIL, new ApiKeyFailException());
			}
		}
	}

	private void loginBySession(HttpSession hs) {
		String userName = (String) hs.getAttribute("username");
		Account a = accountDao.findUserByEmail(userName).get();
		GlobalMethod.login(a);
	}


	private void logRequest(HttpServletRequest httpReq) throws IOException {
		// log request headers
		LOG.info("### Request "+httpReq.getRequestURI()+" Headers:");
		for (String header : Collections.list(httpReq.getHeaderNames())) {
			LOG.log(Level.INFO, "\t* {0}: {1}", new Object[] { header, httpReq.getHeader(header) });
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
