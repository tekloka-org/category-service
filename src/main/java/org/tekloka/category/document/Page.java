package org.tekloka.category.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "pages")
public class Page extends AuditMetadata{

	@Id
	private String pageId;
	
	private String title;
	private String shortTitle;
	private int index;
	
	@Indexed(unique=true)
	private String urlPath;
	
	private String content;
	private boolean approved;
	private boolean active;
	
	private String categoryUrlPath;
	
}
