package xyz.javanew.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.javanew.interceptor.MenuInterceptor;
import xyz.javanew.repository.mongodb.entity.CommonRegularEntity;
import xyz.javanew.repository.mongodb.entity.EscapeCodeEntity;
import xyz.javanew.repository.mongodb.entity.MenuEntity;

@Service
public class InitService {
	private static final Logger logger = Logger.getLogger(InitService.class);
	@Autowired
	private DaoService daoService;
	@Autowired
	private MenuInterceptor menuInterceptor;

	public void initMenus() {
		List<MenuEntity> menuEntities = new ArrayList<MenuEntity>();
		MenuEntity entity = new MenuEntity();
		entity.setId("HOME");
		entity.setSeq(0);
		entity.setNameZh("主页");
		entity.setNameEn("Home");
		entity.setPath("/");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		menuEntities.add(entity);

		List<MenuEntity> subMenus = new ArrayList<MenuEntity>();
		entity = new MenuEntity();
		entity.setId("FUNCTION_DATE");
		entity.setSeq(0);
		entity.setNameZh("日期转换");
		entity.setNameEn("date converter");
		entity.setPath("/function/date");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		subMenus.add(entity);

		entity = new MenuEntity();
		entity.setId("FUNCTION_REG");
		entity.setSeq(1);
		entity.setNameZh("正则表达式");
		entity.setNameEn("regular expression");
		entity.setPath("/function/reg");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		subMenus.add(entity);

		entity = new MenuEntity();
		entity.setId("FUNCTION_FORMATTER");
		entity.setSeq(2);
		entity.setNameZh("格式化");
		entity.setNameEn("formatter");
		entity.setPath("/function/formatter");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		subMenus.add(entity);

		entity = new MenuEntity();
		entity.setId("FUNCTION_COLOR");
		entity.setSeq(3);
		entity.setNameZh("调色板");
		entity.setNameEn("color");
		entity.setPath("/function/color");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		subMenus.add(entity);

		entity = new MenuEntity();
		entity.setId("FUNCTION");
		entity.setHasContent(false);
		entity.setSeq(1);
		entity.setNameZh("功能区");
		entity.setNameEn("function");
		entity.setPath("/function");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setSubMenus(subMenus);
		menuEntities.add(entity);

		daoService.delete(null, MenuEntity.class);
		daoService.insert(menuEntities, MenuEntity.class);
		menuInterceptor.initMenuList();
	}

	public void initCommonRegulars() {
		List<CommonRegularEntity> entities = new ArrayList<CommonRegularEntity>();
		CommonRegularEntity entity = null;
		//
		entity = new CommonRegularEntity();
		entity.setName("手机号");
		entity.setExpression("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
		entities.add(entity);
		//
		entity = new CommonRegularEntity();
		entity.setName("邮箱");
		entity.setExpression("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
		entities.add(entity);
		//
		entity = new CommonRegularEntity();
		entity.setName("身份证");
		entity.setExpression("\\d{15}|\\d{17}[0-9Xx]");
		entities.add(entity);
		//
		entity = new CommonRegularEntity();
		entity.setName("中文");
		entity.setExpression("[\\u4e00-\\u9fa5]");
		entities.add(entity);
		//
		entity = new CommonRegularEntity();
		entity.setName("时间(hh:mm:ss)");
		entity.setExpression("([01]?\\d|2[0-3]):[0-5]?\\d:[0-5]?\\d");
		entities.add(entity);
		//
		entity = new CommonRegularEntity();
		entity.setName("整数");
		entity.setExpression("^-?[1-9]\\d*|0$");
		entities.add(entity);

		entity = new CommonRegularEntity();
		entity.setName("小数");
		entity.setExpression("^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
		entities.add(entity);
		//
		daoService.delete(null, CommonRegularEntity.class);
		daoService.insert(entities, CommonRegularEntity.class);
	}

	public void initEscapeCodes() {
		List<EscapeCodeEntity> entities = new ArrayList<EscapeCodeEntity>();
		EscapeCodeEntity entity = null;

		entity = new EscapeCodeEntity();
		entity.setForJava("\\n");
		entity.setForHtml("<br />");
		entity.setForJs("<br />");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava("\\t");
		entity.setForHtml("<PRE>&#09;</PRE>");
		entity.setForJs("<PRE>&#09;</PRE>");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava("\\s");
		entity.setForHtml("&nbsp;");
		entity.setForJs(" ");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava("<");
		entity.setForHtml("&lt;");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava(">");
		entity.setForHtml("&gt");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava("&");
		entity.setForHtml("&amp;");
		entities.add(entity);

		entity = new EscapeCodeEntity();
		entity.setForJava("'");
		entity.setForHtml("&apos;");
		entities.add(entity);

		//
		daoService.delete(null, EscapeCodeEntity.class);
		daoService.insert(entities, EscapeCodeEntity.class);

	}
}
