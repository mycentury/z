package xyz.javanew.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.javanew.repository.mongodb.entity.FileEntity;
import xyz.javanew.service.DaoService.Condition;

@Service
public class SessionService {
	@Autowired
	private DaoService daoService;

	public void onCreate(HttpSession session) {
		this.countOnlines(session, 1);
		this.executeWhenOn(session);
	}

	public void onDestroy(HttpSession session) {
		this.countOnlines(session, -1);
		this.executeWhenOff(session);
	}

	private void countOnlines(HttpSession session, int change) {
		ServletContext ctx = session.getServletContext();
		Integer onlines = 17;
		if (ctx.getAttribute("onlines") != null) {
			onlines = (Integer) ctx.getAttribute("onlines");
		}
		ctx.setAttribute("onlines", Integer.valueOf(onlines + change));
	}

	private void executeWhenOn(HttpSession session) {
	}

	private void executeWhenOff(HttpSession session) {
		String sessionId = session.getId();
		Condition condition = new Condition();
		condition.addParam("sessionId", "=", sessionId);
		condition.addParam("isTemp", "=", "true");
		daoService.delete(condition, FileEntity.class);
	}

	public void setCheckcode(HttpServletRequest request, String checkcode) {
		request.getSession().setAttribute("checkcode", checkcode);
	}

	public String getCheckcode(HttpServletRequest request) {
		Object checkcode = request.getSession().getAttribute("checkcode");
		return checkcode == null ? null : checkcode.toString();
	}
}
