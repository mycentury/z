/**
 * 
 */
package com.z.view;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.z.repository.mongodb.entity.JokeEntity;

/**
 * @Desc 菜单
 * @author wewenge.yan
 * @Date 2016年12月8日
 * @ClassName MenuEntity
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JokeView extends JokeEntity {
	private static final long serialVersionUID = 1L;
	private Integer praiseCount = 0;// 点赞者
	private Integer belittleCount = 0;// 点踩者
	private Integer commentCount = 0;// 评论者
	private Integer mark = 0;// 该用户1-赞/2-踩
	/**
	 * 启用状态：1-启用，0-禁用
	 */
	private Integer status = 0;
}
