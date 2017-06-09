/**
 * 
 */
package com.z.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.z.service.CacheService;
import com.z.service.DaoService;
import com.z.service.SessionService;

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
public class MenuController {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private DaoService daoService;

	@RequestMapping(value = { "/index" })
	public String home(HttpServletRequest request, ModelMap map) {
		return "direct:/";
	}

	@RequestMapping(value = { "/" })
	public String joke(HttpServletRequest request, ModelMap map) {
		return "index";
	}
}
