/**
 * 
 */
package com.z.repository.mongodb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @desc 用户
 * @author yanwenge
 * @date 2016年12月10日
 * @class UserEntity
 */
@Document(collection = "nick_name")
@Data
@EqualsAndHashCode(callSuper = false)
public class NickNameEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;// 用户名
	/**
	 * 状态：0-录入，1-可用，2-不可用
	 */
	private String status = "0";
}
