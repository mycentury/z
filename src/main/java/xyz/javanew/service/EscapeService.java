/**
 * 
 */
package xyz.javanew.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import xyz.javanew.repository.mongodb.entity.EscapeCodeEntity;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年3月2日
 * @ClassName EscapeUtil
 */
@Service
public class EscapeService {
	private static final Logger logger = Logger.getLogger(EscapeService.class);
	@Autowired
	private DaoService daoService;
	private List<EscapeCodeEntity> escapes;

	public String convertJavaToHtml(String javaString) {
		if (javaString == null) {
			return null;
		}
		if (CollectionUtils.isEmpty(escapes)) {
			initEscapeCodes();
		}
		String htmlString = javaString;
		for (EscapeCodeEntity escape : escapes) {
			if (StringUtils.isEmpty(escape.getForJava())
					|| StringUtils.isEmpty(escape.getForHtml())) {
				continue;
			}
			htmlString = htmlString.replaceAll(escape.getForJava(), escape.getForHtml());
		}
		return htmlString;
	}

	public String convertJavaToJs(String javaString) {
		if (javaString == null) {
			return null;
		}
		if (CollectionUtils.isEmpty(escapes)) {
			initEscapeCodes();
		}
		String jsString = javaString;
		for (EscapeCodeEntity escape : escapes) {
			if (StringUtils.isEmpty(escape.getForJs()) || StringUtils.isEmpty(escape.getForJava())) {
				continue;
			}
			jsString = jsString.replaceAll(escape.getForJava(), escape.getForJs());
		}
		return jsString;
	}

	public String convertHtmlToJava(String htmlString) {
		if (htmlString == null) {
			return null;
		}
		String javaString = htmlString;
		for (EscapeCodeEntity escape : escapes) {
			if (StringUtils.isEmpty(escape.getForHtml())
					|| StringUtils.isEmpty(escape.getForJava())) {
				continue;
			}
			javaString = javaString.replace(escape.getForHtml(), escape.getForJava());
		}
		return javaString;
	}

	public void initEscapeCodes() {
		if (!CollectionUtils.isEmpty(escapes)) {
			escapes.clear();
		}
		escapes = daoService.query(null, EscapeCodeEntity.class);
		if (CollectionUtils.isEmpty(escapes)) {
			escapes = new ArrayList<EscapeCodeEntity>();
			logger.error("escapes初始化失败！");
		}
	}
}
