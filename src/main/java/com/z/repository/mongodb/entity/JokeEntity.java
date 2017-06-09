/**
 * 
 */
package com.z.repository.mongodb.entity;

import java.util.List;

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
@Document(collection = "joke")
@Data
@EqualsAndHashCode(callSuper = false)
public class JokeEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String source;
	private String sourceId;
	private String type;// 0-段子,1-静图,2-动图,3-视频
	private String isFoul;// 1-荤段子，2-素段子
	private List<String> labels;
	private Integer categoryType;
	private Integer label;
	private Integer sourcetype;
	private String text;
	private List<Media> images;
	private List<Media> videos;
	private String userId;
	private String userPhoto;
	private String publishDate;
	private String praisers;// 点赞者
	private Integer praiseCount = 0;// 点赞者
	private String belittlers;// 点踩者
	private Integer belittleCount = 0;// 点踩者
	private List<CommentEntity> comments;// 评论者
	private Integer commentCount = 0;// 评论者
	private Integer mark = 0;// 该用户1-赞/2-踩
	/**
	 * 启用状态：1-启用，0-禁用，负标示待处理，正表示已处理（删除等）
	 */
	private Integer status = -2;
}
