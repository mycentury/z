/**
 * 
 */
package xyz.javanew.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.javanew.repository.mongodb.entity.CommonRegularEntity;
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
		List<CommonRegularEntity> allRegulars = daoService.query(null, CommonRegularEntity.class);
		map.put("commonRegulars", allRegulars);
		return "function/reg";
	}
}
