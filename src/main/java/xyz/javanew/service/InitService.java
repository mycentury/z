package xyz.javanew.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.javanew.repository.mongodb.entity.MenuEntity;

@Service
public class InitService {
	private static final Logger logger = Logger.getLogger(InitService.class);
	@Autowired
	private DaoService daoService;

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
		entity.setId("FUNCTION");
		entity.setSeq(1);
		entity.setNameZh("功能区");
		entity.setNameEn("function");
		entity.setPath("/function");
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setSubMenus(subMenus);
		menuEntities.add(entity);

		daoService.insert(menuEntities, MenuEntity.class);
	}

	public void initTasks() {
	}
}
