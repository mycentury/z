/**
 * 
 */
package xyz.javanew.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年12月26日
 * @ClassName GsonUtil
 */
public class GsonUtil {
	private final static Gson gson = new Gson();

	public static <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		// Type jsonType = new TypeToken<Map<String, Integer>>() {}.getType();
		return gson.fromJson(json, typeOfT);
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static void readJson(String sourceJson) {
		if (StringUtils.isEmpty(sourceJson)) {
			return;
		}
		boolean isArray = sourceJson.startsWith("[");
		StringReader stringReader = null;
		JsonReader jsonReader = null;
		try {
			stringReader = new StringReader(sourceJson);
			jsonReader = new JsonReader(stringReader);
			if (isArray) {
				jsonReader.beginArray();
			} else {
				jsonReader.beginObject();
			}
			while (jsonReader.hasNext()) {
				String key = jsonReader.nextName();
				Object value = null;
				try {
					value = jsonReader.nextString();
					System.out.println(key + "," + value);
				} catch (Exception e) {
					jsonReader.beginObject();
					while (jsonReader.hasNext()) {
						String key1 = jsonReader.nextName();
						Object value1 = jsonReader.nextString();
						System.out.println(key1 + "," + value1);
					}
				}
			}
			if (isArray) {
				jsonReader.endArray();
			} else {
				jsonReader.endObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jsonReader != null) {
				try {
					jsonReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stringReader != null) {
				stringReader.close();
			}
		}
	}

	public static String formatJson(String sourceJson) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isEmpty(sourceJson)) {
			return sb.toString();
		}
		Pattern pattern = Pattern.compile("\\{|\\[|\\]|\\}");
		String startPattern = "\\{|\\[";
		Matcher matcher = pattern.matcher(sourceJson);
		int lastIndex = 0;
		int currIndex = 0;
		int tabCount = 0;
		while (matcher.find()) {
			String group = matcher.group();
			currIndex = matcher.start();
			String tabsByCount = getTabsByCount(tabCount);
			String before = sourceJson.substring(lastIndex, currIndex);
			before = before.replace(",", ",\n" + tabsByCount);
			if (before.endsWith(":")) {
				before += "\n" + tabsByCount;
			}
			if (group.matches(startPattern)) {
				tabsByCount = getTabsByCount(++tabCount);
				sb.append(before).append(group).append("\n").append(tabsByCount);
			} else {
				tabsByCount = getTabsByCount(--tabCount);
				sb.append(before).append("\n").append(tabsByCount).append(group);
			}
			lastIndex = matcher.end();
		}
		return sb.toString();
	}

	/**
	 * @param tabCount
	 * @param sb
	 */
	private static String getTabsByCount(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tabCount; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String properties = "[{\"age\":28,\"gender\":\"male\"},{\"age\":28,\"gender\":\"male\"}]";
		String formatJson = formatJson("[{\"name\":\"yanwenge\",\"properties\":" + properties + "},{\"name\":\"yanwenge\",\"properties\":"
				+ properties + "}]");
		System.out.println(formatJson);
	}
}
