/**
 * 
 */
package xyz.javanew.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

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
	public void changeLanguage(HttpServletRequest request, HttpServletResponse response, String locale) {
		RequestContextUtils.getLocaleResolver(request).setLocale(request, response, "en".equals(locale) ? Locale.CHINA : Locale.US);
		localeResolver.setLocale(request, response, "en".equals(locale) ? Locale.CHINA : Locale.US);
		LocaleContextHolder.setLocale("en".equals(locale) ? Locale.CHINA : Locale.US);
	}
}
