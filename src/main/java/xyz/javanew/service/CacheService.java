/**
 * 
 */
package xyz.javanew.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xyz.javanew.repository.mongodb.entity.MenuEntity;
import xyz.javanew.util.FilePathUtil;

/**
 * @Desc 统一管理应用缓存
 * @author wewenge.yan
 * @Date 2017年3月13日
 * @ClassName CacheService
 */
@Service
public class CacheService {

	@Value("${file.upload.folder}")
	private String uploadFolder;
	private String uploadPath;
	private String BASE_URL;

	private List<MenuEntity> menuList;
	@Autowired
	private DaoService daoService;

	public String getUploadFolder() {
		return uploadFolder;
	}

	public String getUploadAbsolutePath() {
		if (StringUtils.isEmpty(uploadPath)) {
			uploadPath = FilePathUtil.getRealFilePath("../" + uploadFolder);
			File dir = new File(uploadPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return uploadPath;
	}

	public List<MenuEntity> getMenuList() {
		if (menuList == null) {
			this.initMenuList();
		}
		return menuList;
	}

	public void initMenuList() {
		menuList = daoService.query(null, MenuEntity.class);
	}

	public String getBaseUrl(HttpServletRequest request) {
		if (StringUtils.isEmpty(BASE_URL)) {
			this.initBaseUrl(request);
		}
		return BASE_URL;
	}

	private void initBaseUrl(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(request.getServerName());
		if (request.getServerPort() > 0) {
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		BASE_URL = sb.toString();
	}
}
