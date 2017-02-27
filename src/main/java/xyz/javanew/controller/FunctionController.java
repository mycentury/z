/**
 * 
 */
package xyz.javanew.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.javanew.service.DaoService;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年12月16日
 * @ClassName HistoryController
 */
@Controller
@RequestMapping(value = { "function" })
public class FunctionController {
	@Autowired
	private DaoService daoService;

	@RequestMapping(value = { "" })
	public String index(HttpServletRequest request, ModelMap map) {
		return "function/index";
	}

	@RequestMapping(value = { "date" })
	public String date(HttpServletRequest request, ModelMap map) {
		return "function/date";
	}

	@RequestMapping(value = { "reg" })
	public String reg(HttpServletRequest request, ModelMap map) {
		return "function/reg";
	}
}
