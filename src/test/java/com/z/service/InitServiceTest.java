/**
 * 
 */
package com.z.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.z.BaseTest;
import com.z.repository.mongodb.entity.JokeEntity;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年2月27日
 * @ClassName InitServiceTest
 */
public class InitServiceTest extends BaseTest {

	@Autowired
	private InitService initService;
	@Autowired
	private DaoService daoService;

	/**
	 * Test method for {@link com.z.service.InitService#initMenus()}.
	 */
	@Test
	public void testInitMenus() {
		initService.initMenus();
	}

	/**
	 * Test method for {@link com.z.service.InitService#initSysConfigs()}.
	 */
	@Test
	public void testInitSysConfigs() {
		initService.initSysConfigs();
	}

	/**
	 * Test method for {@link com.z.service.InitService#initSystemUsers()}.
	 */
	@Test
	public void testInitSystemUsers() {
		initService.initSystemUsers();
	}

	@Test
	public void testInitRules() {
		initService.initRules();
	}

	@Test
	public void testInitTasks() {
		initService.initTasks();
	}

	@Test
	public void testInitNickNames() {
		initService.initNickNames();
	}

	@Test
	public void test() {
		List<JokeEntity> query = daoService.query(null, JokeEntity.class);
		for (JokeEntity jokeEntity : query) {
			// jokeEntity.setPublisherPhoto("/img/logo.jpg");
			jokeEntity.setText(jokeEntity.getText().replaceAll("💛", ""));
			jokeEntity.setPraiseCount(0);
			jokeEntity.setBelittleCount(0);
		}
		daoService.delete(null, JokeEntity.class);
		daoService.insert(query, JokeEntity.class);
	}
}
