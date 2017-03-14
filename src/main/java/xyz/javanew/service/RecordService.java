package xyz.javanew.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xyz.javanew.constant.OperType;
import xyz.javanew.constant.SysConfig;
import xyz.javanew.repository.mongodb.entity.RecordEntity;
import xyz.javanew.util.AddressUtil;

@Service
public class RecordService {
	@Autowired
	private DaoService daoService;
	@Autowired
	private CacheService cacheService;

	@Value("${log.record.open}")
	private boolean openRecord;

	public void insert(RecordEntity record) {
		if (!openRecord) {
			return;
		}
		Date createTime = new Date();
		record.setCreateTime(createTime);
		record.setUpdateTime(createTime);
		daoService.insert(record);
	}

	/**
	 * @param request
	 * @return
	 */
	public RecordEntity assembleRocordEntity(HttpServletRequest request) {
		RecordEntity record = new RecordEntity();
		if (!openRecord) {
			return record;
		}
		record.setOpertype(OperType.REQUEST.name());
		record.setUsername(String.valueOf(request.getSession().getAttribute("username")));
		record.setUsertype(String.valueOf(request.getSession().getAttribute("usertype")));
		if (StringUtils.isEmpty(record.getUsername())) {
			record.setUsername(String.valueOf(request.getAttribute("username")));
			record.setUsertype(String.valueOf(request.getAttribute("usertype")));
		}

		String baseUrl = cacheService.getBaseUrl(SysConfig.BASE_URL);
		String before = request.getHeader("Referer");
		String after = request.getRequestURL().toString();
		if (!StringUtils.isEmpty(before)) {
			before = before.replace(baseUrl, "");
		}
		if (!StringUtils.isEmpty(after)) {
			after = after.replace(baseUrl, "");
		}
		record.setBefore(before);
		record.setAfter(after);
		record.setIp(AddressUtil.getIpAddress(request));
		return record;
	}
}
