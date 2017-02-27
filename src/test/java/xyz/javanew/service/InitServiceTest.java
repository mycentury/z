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

}
