package xyz.javanew.repository.mongodb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image")
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	private String url;
	private String userId;
}
