package org.tekloka.category.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.ResponseEntity;
import org.tekloka.category.dto.PageDTO;
import org.tekloka.category.dto.PageLinkDTO;

public interface PageService {

	ResponseEntity<Object> save(HttpServletRequest request, PageDTO pageDTO);

	ResponseEntity<Object> getCategoryPageLinks(HttpServletRequest request, String categoryUrlPath);

	ResponseEntity<Object> getCategoryPage(HttpServletRequest request, String categoryUrlPath, String pageUrlPath);

	ResponseEntity<Object> update(HttpServletRequest request, PageDTO pageDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, PageDTO pageDTO);

	ResponseEntity<Object> updatePageLinks(HttpServletRequest request, List<PageLinkDTO> pageLinks);

}
