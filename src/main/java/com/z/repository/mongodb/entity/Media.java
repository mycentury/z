package com.z.repository.mongodb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Media {
	private String sourceUrl;// 源地址
	private String originalUrl;// 原图
	private String unmarkedUrl;// 去水印图
	private String markedUrl;// 水印图(展示)
}
