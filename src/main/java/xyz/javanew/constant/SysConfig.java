/**
 * 
 */
package xyz.javanew.constant;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2016年12月28日
 * @ClassName SysConfig
 */
public enum SysConfig {
	BASE_URL("JN-00-00", "基础路径、域名");
	private String id;
	private String desc;

	private SysConfig(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
}
