package org.tekloka.category.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "categories")
public class Category extends AuditMetadata{

	@Id
	private String categoryId;
	
	private String name;
	
	@Indexed(unique=true)
	private String urlPath;
	
	private boolean active;
	
}
