/**
 * 
 */
package xyz.javanew.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.javanew.BaseTest;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年2月27日
 * @ClassName InitServiceTest
 */
public class InitServiceTest extends BaseTest {

	@Autowired
	private InitService initService;

	/**
	 * Test method for {@link xyz.javanew.service.InitService#initMenus()}.
	 */
	@Test
	public void testInitMenus() {
		initService.initMenus();
	}

	/**
	 * Test method for {@link xyz.javanew.service.InitService#initCommonRegulars()}.
	 */
	@Test
	public void testInitCommonRegulars() {
		initService.initCommonRegulars();
	}

	/**
	 * Test method for {@link xyz.javanew.service.InitService#initEscapeCodes()}.
	 */
	@Test
	public void testInitEscapeCodes() {
		initService.initEscapeCodes();
	}

	/**
	 * Test method for {@link xyz.javanew.service.InitService#initSysConfigs()}.
	 */
	@Test
	public void testInitSysConfigs() {
		initService.initSysConfigs();
	}

	@Test
	public void test() {
		long millis = System.currentTimeMillis();
		System.out.println(millis);
		long millisecond = millis % 1000;
		long second = millis / 1000;
		long minute = millis / 1000 / 60;
		long hour = millis / 1000 / 60 / 60;
		long date = millis / 1000 / 60 / 60;
		System.out.println(millis);
	}
}
