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
@Document(collection = "comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	/**
	 * 类型
	 */
	private String type;// 1-joke，2-picture,3-video
	private String jokeId;
	private String seq;
	private String toRemarkId;
	/**
	 * 内容
	 */
	private String text;
	private String publisherId;
	private String publisherName;
	private String publishDate;
	private String praisers;// 点赞者
	private String praiseCount;// 点赞者
	private String belittlers;// 点踩者
	private String belittleCount;// 点踩者
	/**
	 * 启用状态：1-启用，0-禁用
	 */
	private Integer status = 0;
}
