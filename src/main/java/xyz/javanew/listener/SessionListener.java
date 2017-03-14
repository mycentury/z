/**
 * 
 */
package xyz.javanew.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import xyz.javanew.service.SessionService;
import xyz.javanew.util.SpringContextUtil;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年3月14日
 * @ClassName SessionListener
 */
@Component
public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		SessionService bean = SpringContextUtil.getBean(SessionService.class);
		bean.onCreate(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		SessionService bean = SpringContextUtil.getBean(SessionService.class);
		bean.onDestroy(se.getSession());
	}
}
