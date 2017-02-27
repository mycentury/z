package xyz.javanew.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class SessionService {

	public void setCheckcode(HttpServletRequest request, String checkcode) {
		request.getSession().setAttribute("checkcode", checkcode);
	}

	public String getCheckcode(HttpServletRequest request) {
		Object checkcode = request.getSession().getAttribute("checkcode");
		return checkcode == null ? null : checkcode.toString();
	}
}
