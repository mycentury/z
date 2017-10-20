/**
 * 
 */
package com.z.repository.mongodb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Desc 菜单
 * @author wewenge.yan
 * @Date 2016年12月8日
 * @ClassName MenuEntity
 */
@Document(collection = "mark")
@Data
@EqualsAndHashCode(callSuper = false)
public class MarkEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	/**
	 * 类型
	 */
	private String type;// 1-顶，2-踩
	private String jokeId;
	private String userId;
	private String markTime;
	/**
	 * 启用状态：1-启用，0-禁用
	 */
	private Integer status = 0;
}
