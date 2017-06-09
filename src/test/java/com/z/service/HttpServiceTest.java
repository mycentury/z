/**
 * 
 */
package com.z.service;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.z.BaseTest;
import com.z.constant.Method;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年3月9日
 * @ClassName HttpServiceTest
 */
public class HttpServiceTest extends BaseTest {

	@Autowired
	private HttpService httpService;

	private String url = "http://www.baidu.com/s";

	/**
	 * Test method for
	 * {@link com.z.service.HttpService#requestByRestTemplate(java.lang.String, com.z.constant.Method, java.lang.Object, java.lang.Class, java.util.Map)}
	 * .
	 */
	@Test
	public void testRequestByRestTemplate() {
		String response = httpService
				.requestByRestTemplate(url, Method.POST, null, String.class, 4);
		System.out.println(response);
		response = httpService.requestByRestTemplate(url, Method.GET, null, String.class, 4);
		System.out.println(response);
	}

	/**
	 * Test method for
	 * {@link com.z.service.HttpService#requestByJsoup(java.lang.String, com.z.constant.Method, java.lang.Object)}
	 * .
	 */
	@Test
	public void testRequestByJsoup() {
		Document response = httpService.requestByJsoup(url, Method.POST, null, 4);
		System.out.println(response);
		response = httpService.requestByJsoup(url, Method.GET, null, 4);
		System.out.println(response);
	}

	/**
	 * Test method for
	 * {@link com.z.service.HttpService#request(java.lang.String, com.z.constant.Method, java.lang.Object)}
	 * .
	 */
	@Test
	public void testRequest() {
		String response = httpService.request(url, Method.POST, null, 3);
		System.out.println(response);
		response = httpService.request(url, Method.GET, null, 3);
		System.out.println(response);
	}

}
