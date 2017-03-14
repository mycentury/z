/**
 * 
 */
package xyz.javanew.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.javanew.constant.ResultType;
import xyz.javanew.constant.SysConfig;
import xyz.javanew.domain.Result;
import xyz.javanew.service.CacheService;
import xyz.javanew.util.QrcodeUtil;
import xyz.javanew.util.QrcodeUtil.QrcodeInfo;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年11月25日
 * @ClassName LotterySsqController
 */
/**
 * @author yanwenge
 */
@Controller
public class IndexController {
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = { "/index" })
	public String index(HttpServletRequest request, ModelMap map) {
		return "direct:/";
	}

	@RequestMapping(value = { "/" })
	public String home(HttpServletRequest request, ModelMap map) {
		return "index";
	}

	@RequestMapping(value = { "/qrcode" })
	public @ResponseBody Result<String> qrcode(HttpServletRequest request, ModelMap map, QrcodeInfo qrcodeInfo) {
		Result<String> result = new Result<String>();
		try {
			String uploadAbsolutePath = cacheService.getUploadAbsolutePath();
			qrcodeInfo.setQrcodePath(uploadAbsolutePath);
			String baseUrl = cacheService.getBaseUrl(SysConfig.BASE_URL);
			String logoPath = qrcodeInfo.getLogoPath();
			if (StringUtils.hasText(logoPath)) {
				logoPath = qrcodeInfo.getLogoPath().replace(baseUrl + "/" + cacheService.getUploadFolder(), uploadAbsolutePath);
				qrcodeInfo.setLogoPath(logoPath);
			}
			qrcodeInfo.setQrcodeName("temp-qrcode");
			qrcodeInfo.setBackground(qrcodeInfo.getBackground().replace("#", ""));
			qrcodeInfo.setForeground(qrcodeInfo.getForeground().replace("#", ""));
			String generateQrcode = QrcodeUtil.generateQrcode(qrcodeInfo);
			result.setData(generateQrcode.replace(uploadAbsolutePath, baseUrl + "/" + cacheService.getUploadFolder()));
			result.setResultStatusAndMsg(ResultType.SUCCESS, null);
		} catch (Exception e) {
			result.setResultStatusAndMsg(ResultType.SERVICE_ERROR, null);
		}
		return result;
	}

	@RequestMapping(value = { "/qrcode/parse" })
	public @ResponseBody Result<String> parseQrcode(HttpServletRequest request, ModelMap map, String qrcodeImage) {
		Result<String> result = new Result<String>();
		try {
			String uploadAbsolutePath = cacheService.getUploadAbsolutePath();
			String baseUrl = cacheService.getBaseUrl(SysConfig.BASE_URL);
			if (StringUtils.hasText(qrcodeImage)) {
				qrcodeImage = qrcodeImage.replace(baseUrl + "/" + cacheService.getUploadFolder(), uploadAbsolutePath);
			}
			String qrcodeText = QrcodeUtil.parseQrcode(qrcodeImage);
			result.setData(qrcodeText);
			result.setResultStatusAndMsg(ResultType.SUCCESS, null);
		} catch (Exception e) {
			result.setResultStatusAndMsg(ResultType.SERVICE_ERROR, null);
		}
		return result;
	}
}
