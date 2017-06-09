/**
 * 
 */
package com.z.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.z.constant.OperType;
import com.z.constant.ResultType;
import com.z.domain.Result;
import com.z.repository.mongodb.entity.RecordEntity;
import com.z.repository.mongodb.entity.UserEntity;
import com.z.service.DaoService;
import com.z.service.RecordService;
import com.z.service.SessionService;
import com.z.service.DaoService.Condition;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年11月25日
 * @ClassName LotterySsqController
 */
/**
 * @author yanwenge
 */
@Controller
@RequestMapping("user")
public class UserController {
	private final static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private RecordService recordService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private DaoService daoService;

	@RequestMapping("login")
	public String login(HttpServletRequest request, ModelMap map) {
		if (!StringUtils.isEmpty(sessionService.getUsername(request))) {
			return "redirect:/";
		}
		String errorMsg = request.getParameter("errorMsg");
		if (!StringUtils.isEmpty(errorMsg)) {
			map.put("errorMsg", errorMsg);
		}
		return sessionService.isMobile(request) ? "user/m_login" : "user/login";
	}

	@RequestMapping(value = "user_login", method = { RequestMethod.POST })
	public String userLogin(HttpServletRequest request, UserEntity user, String checkcode, RedirectAttributes attr) {
		attr.addFlashAttribute("user", user);
		RecordEntity record = recordService.assembleRocordEntity(request);
		record.setOpertype(OperType.LOGIN.name());
		if (StringUtils.isEmpty(record.getUsername())) {
			record.setUsername(user.getId());
			record.setUsertype(user.getUsertype());
		}
		if (!checkcode.equalsIgnoreCase(sessionService.getCheckcode(request))) {
			attr.addFlashAttribute("errorMsg", "验证码错误！");
			record.setAfter(checkcode + "-验证码错误！");
			recordService.insert(record);
			return "redirect:/user/login";
		}
		Condition condition = new Condition();
		condition.addParam("id", "=", user.getId());
		condition.addParam("password", "=", user.getPassword());
		boolean exists = daoService.exists(condition, UserEntity.class);
		if (!exists) {
			attr.addFlashAttribute("errorMsg", "username or password error");
			record.setAfter(user.getId() + "/" + user.getPassword() + "-username or password error");
			recordService.insert(record);
			return "redirect:/user/login";
		}

		sessionService.setUsername(request, user.getId());
		record.setAfter(user.getId() + "-login successfully");
		recordService.insert(record);
		return "redirect:/";
	}

	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		sessionService.removeUsername(request);
		return "redirect:/user/login";
	}

	@RequestMapping("register")
	public String register(HttpServletRequest request, ModelMap map) {
		return sessionService.isMobile(request) ? "user/m_register" : "user/register";
	}

	@RequestMapping("check_username")
	public @ResponseBody Result<Boolean> checkUsername(HttpServletRequest request, String username, ModelMap map) {
		Result<Boolean> result = new Result<Boolean>();
		boolean exists = daoService.existsById(username, UserEntity.class);
		if (exists) {
			result.setResultStatusAndMsg(ResultType.USER_EXISTS, null);
			result.setData(false);
			return result;
		}
		result.setResultStatusAndMsg(ResultType.SUCCESS, null);
		result.setData(false);
		return result;
	}

	@RequestMapping(value = "user_register", method = { RequestMethod.POST })
	public String register(HttpServletRequest request, UserEntity user, RedirectAttributes attr) {
		attr.addFlashAttribute("user", user);
		RecordEntity record = recordService.assembleRocordEntity(request);
		record.setOpertype(OperType.REGISTER.name());
		boolean exists = daoService.existsById(user.getId(), UserEntity.class);
		if (exists) {
			record.setAfter(user.getId() + "-userId allready exists");
			recordService.insert(record);
			attr.addFlashAttribute("errorMsg", "username allready exists");
			return "redirect:/user/register";
		}
		user.setUsertype("G");
		daoService.insert(user);
		sessionService.setUsername(request, user.getId());
		record.setAfter(user.getId() + "-register successfully！");
		recordService.insert(record);
		return "redirect:/";
	}
}
