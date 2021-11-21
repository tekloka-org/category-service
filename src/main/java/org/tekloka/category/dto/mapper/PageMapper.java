package org.tekloka.category.dto.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.tekloka.category.document.Page;
import org.tekloka.category.dto.PageDTO;

@Component
public class PageMapper {
	
	public Page toPage(Optional<Page> pageOptional, PageDTO pageDTO) {
		var page = new Page();
		if(pageOptional.isPresent()) {
			page = pageOptional.get();
		}		
		page.setTitle(pageDTO.getTitle());
		page.setShortTitle(pageDTO.getShortTitle());
		page.setUrlPath(pageDTO.getUrlPath());
		page.setContent(pageDTO.getContent());
		page.setCategoryUrlPath(pageDTO.getCategoryUrlPath());
		return page;
	}
	
	public PageDTO toPageDTO(Page page) {
		var pageDTO = new PageDTO();
		pageDTO.setPageId(page.getPageId());
		pageDTO.setTitle(page.getTitle());
		pageDTO.setShortTitle(page.getShortTitle());
		pageDTO.setUrlPath(page.getUrlPath());
		pageDTO.setContent(page.getContent());
		pageDTO.setCategoryUrlPath(page.getCategoryUrlPath());
		return pageDTO;
	}
	
	public Set<PageDTO> toPageDTOSet(Set<Page> pages){
		return pages.stream().map(this::toPageDTO).collect(Collectors.toSet());
	}

}
