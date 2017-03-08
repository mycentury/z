/**
 * 
 */
package xyz.javanew.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.javanew.constant.Constant;
import xyz.javanew.constant.ResultType;
import xyz.javanew.domain.Result;
import xyz.javanew.repository.mongodb.entity.CommonRegularEntity;
import xyz.javanew.service.DaoService;
import xyz.javanew.service.EscapeService;
import xyz.javanew.util.GsonUtil;

import com.google.gson.JsonElement;

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

	@Autowired
	private EscapeService escapeService;

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

	@RequestMapping(value = { "formatter" })
	public String formatter(HttpServletRequest request, ModelMap map) {
		return "function/formatter";
	}

	@RequestMapping(value = { "format" })
	public @ResponseBody Result<String> format(HttpServletRequest request, ModelMap map, String source) {
		Result<String> result = new Result<String>();
		JsonElement readJson = GsonUtil.readJson(source);
		boolean isJson = readJson != null;
		result.setStatus(isJson ? ResultType.SUCCESS.getStatus() : ResultType.PARAMETER_ERROR.getStatus());
		result.setMessage(isJson ? "校验通过：格式正确" : "校验不通过：格式可能错误！");
		String formatJson = GsonUtil.formatJson(source);
		formatJson = escapeService.convertJavaToJs(formatJson);
		result.setData(formatJson);
		return result;
	}

	@RequestMapping(value = { "color" })
	public String oschina(HttpServletRequest request, ModelMap map, String source) {
		List<List<String>> commonColors = new ArrayList<List<String>>();
		List<List<String>> otherColors = new ArrayList<List<String>>();
		for (int i = 0; i < Constant.SAFE_COLOR.length; i++) {
			for (int j = 0; j < Constant.SAFE_COLOR.length; j++) {
				List<String> commonList = new ArrayList<String>();
				List<String> otherList = new ArrayList<String>();
				for (int k = 0; k < Constant.SAFE_COLOR.length; k++) {
					String color = Constant.SAFE_COLOR[i] + Constant.SAFE_COLOR[j] + Constant.SAFE_COLOR[k];
					if (k % 3 == 0) {
						commonList.add(color);
					} else {
						otherList.add(color);
					}
				}
				if (i % 3 == 0 && j % 3 == 0) {
					commonColors.add(commonList);
				} else {
					otherColors.add(otherList);
				}
			}
		}
		map.put("commonColors", commonColors);
		map.put("otherColors", otherColors);
		return "function/color";
	}
}
