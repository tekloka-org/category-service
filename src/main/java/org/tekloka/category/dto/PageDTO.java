package org.tekloka.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {
	
	private String pageId;
	private String title;
	private String shortTitle;
	private String urlPath;
	private int index;
	private String content;
	private String categoryUrlPath;
	
}
