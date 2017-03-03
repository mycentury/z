/**
 * 
 */
package xyz.javanew.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年2月27日
 * @ClassName LanguageController
 */
@Controller
@RequestMapping("language")
public class LanguageController {
	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping("change")
	public void changeLanguage(HttpServletRequest request, HttpServletResponse response, String language) {
		// (1)
		// RequestContextUtils.getLocaleResolver(request).setLocale(request, response, "en".equals(language) ? Locale.US
		// : Locale.CHINA);
		// (2)
		localeResolver.setLocale(request, response, "en".equals(language) ? Locale.US : Locale.CHINA);
		// (3)从页面获取，组合页面有问题
		// LocaleContextHolder.setLocale("en".equals(language) ? Locale.US : Locale.CHINA);
	}
}
