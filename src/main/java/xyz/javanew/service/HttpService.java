/**
 * 
 */
package xyz.javanew.service;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import xyz.javanew.constant.Method;
import xyz.javanew.util.TypeConverterUtil;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年3月9日
 * @ClassName HttpService
 */
@Service
public class HttpService {
	@Value("${http.connect.timeout}")
	private Integer connectTimeout;
	@Value("${http.socket.timeout}")
	private Integer socketTimeout;

	@Autowired
	private RestTemplate restTemplate;

	public <T> T requestByRestTemplate(String url, Method method, Object request, Class<T> responseType) {
		if (Method.POST.equals(method)) {
			return restTemplate.postForObject(url, request, responseType);
		} else {
			url += request == null ? "" : this.generateParamUrlByRequest(request);
			return restTemplate.getForObject(url, responseType);
		}
	}

	public Document requestByJsoup(String url, Method method, Object request) {
		Document result = null;
		try {
			if (Method.POST.equals(method)) {
				Connection connect = Jsoup.connect(url);
				result = connect.post();
			} else {
				url += request == null ? "" : this.generateParamUrlByRequest(request);
				Connection connect = Jsoup.connect(url);
				result = connect.get();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String request(String url, Method method, Object request) {
		Request req = null;
		if (Method.POST.equals(method)) {
			req = Request.Post(url);
			req.bodyString(this.generateParamUrlByRequest(request), ContentType.APPLICATION_JSON);
		} else {
			req = Request.Get(url).addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
		}
		req.connectTimeout(connectTimeout).socketTimeout(socketTimeout);
		try {
			return req.execute().returnContent().asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generateParamUrlByRequest(Object request) {
		Map<String, Object> map = TypeConverterUtil.changeSourceToMap(request);
		StringBuilder result = new StringBuilder();
		int i = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			result.append(i == 0 ? "?" : "&");
			result.append(entry.getKey()).append("=").append(entry.getValue());
			i++;
		}
		return result.toString();
	}
}
