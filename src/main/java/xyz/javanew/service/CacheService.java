/**
 * 
 */
package xyz.javanew.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xyz.javanew.constant.SysConfig;
import xyz.javanew.repository.mongodb.entity.MenuEntity;
import xyz.javanew.repository.mongodb.entity.RecordEntity;
import xyz.javanew.repository.mongodb.entity.SysConfigEntity;
import xyz.javanew.util.FilePathUtil;

/**
 * @Desc 统一管理应用缓存
 * @author wewenge.yan
 * @Date 2017年3月13日
 * @ClassName CacheService
 */
@Service
public class CacheService implements InitializingBean {

	@Value("${file.upload.folder}")
	private String uploadFolder;
	private String uploadPath;

	private List<MenuEntity> menuList;
	private Map<String, Integer> accessFrequencyMap = new HashMap<String, Integer>();
	private Map<String, String> sysConfigMap = new HashMap<String, String>();
	@Autowired
	private DaoService daoService;

	public String getUploadFolder() {
		return uploadFolder;
	}

	public Map<String, Integer> getAccessFrequencyMap() {
		return accessFrequencyMap;
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

	public String getBaseUrl(SysConfig sysConfig) {
		return sysConfigMap.get(sysConfig.getId());
	}

	private void initSysConfigMap() {
		List<SysConfigEntity> allConfigs = daoService.query(null, SysConfigEntity.class);
		for (SysConfigEntity sysConfig : allConfigs) {
			sysConfigMap.put(sysConfig.getId(), sysConfig.getValue());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initSysConfigMap();
		this.initAccessFrequencyMap();
	}

	/**
	 * 
	 */
	private void initAccessFrequencyMap() {
		List<RecordEntity> allRecords = daoService.query(null, RecordEntity.class);
		for (RecordEntity recordEntity : allRecords) {
			this.updateAccessFrequencyMap(recordEntity);
		}
	}

	public void updateAccessFrequencyMap(RecordEntity recordEntity) {
		String key = recordEntity.getAfter();
		Integer value = accessFrequencyMap.get(key);
		if (value == null) {
			value = 0;
		}
		accessFrequencyMap.put(key, value + 1);
	}
}
