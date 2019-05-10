package net.surfm.account.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {
	private final static Logger LOG = Logger.getLogger(SessionListener.class.getName());
	private static Map<String, HttpSession> map = new ConcurrentHashMap<String, HttpSession>();

	public void sessionCreated(HttpSessionEvent event) {
		String id = event.getSession().getId();
		LOG.log(Level.INFO, "session created : " + id);
		addHttpSession(event.getSession());
		String userName = (String) event.getSession().getAttribute("username");
		if (userName != null) {
			System.out.println("User name: " + userName);
		}
	}

	public static HttpSession getHttpSession(String sessionID) {
		return map.get(sessionID);
	}
	
	public static void addHttpSession(HttpSession hs){
		map.put(hs.getId(), hs);
	}
	

	public void sessionDestroyed(HttpSessionEvent event) {
		map.remove(event.getSession().getId());
		System.out.println("sessionDestroyed="+event.getSession().getId());
	}
}