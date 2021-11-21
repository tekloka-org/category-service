package org.tekloka.category.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.category.constants.AppConstants;
import org.tekloka.category.constants.DataConstants;
import org.tekloka.category.constants.ResponseConstants;
import org.tekloka.category.document.Category;
import org.tekloka.category.document.Page;
import org.tekloka.category.dto.PageDTO;
import org.tekloka.category.dto.PageLinkDTO;
import org.tekloka.category.dto.mapper.PageMapper;
import org.tekloka.category.repository.PageRepository;
import org.tekloka.category.service.CategoryService;
import org.tekloka.category.service.PageService;
import org.tekloka.category.util.ResponseUtil;


@Service
public class PageServiceImpl implements PageService {

	private final Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);
	private final PageRepository pageRepository;
	private final ResponseUtil responseUtil;
	private final CategoryService categoryService;
	private final PageMapper pageMapper;
	
	public PageServiceImpl(PageRepository pageRepository, ResponseUtil responseUtil,
			CategoryService categoryService, PageMapper pageMapper) {
		this.pageRepository = pageRepository;
		this.responseUtil = responseUtil;
		this.categoryService = categoryService;
		this.pageMapper = pageMapper;
	}
	
	public Set<Page> findByCategoryUrlPathAndActive(String categoryId, boolean active){
		return pageRepository.findByCategoryUrlPathAndActive(categoryId, active);
	}
	
	public Optional<Page> findByCategoryUrlPathAndUrlPathAndActive(String categoryId, String pageUrlPath, boolean active){
		return pageRepository.findByCategoryUrlPathAndUrlPathAndActive(categoryId, pageUrlPath, active);
	}
	
	public Set<PageLinkDTO> findCategoryPages(String categoryId, boolean active){
		return pageRepository.findCategoryPages(categoryId, active);
	}
	
	public Page toPage(Optional<Page> pageOptional, PageDTO pageDTO) {
		return pageMapper.toPage(pageOptional, pageDTO);
	}
	
	public PageDTO toPageDTO(Page page) {
		return pageMapper.toPageDTO(page);
	}
	
	public Page save(Page page) {
		return pageRepository.save(page);
	}
	
	public Optional<Page> findByPageId(String pageId){
		return pageRepository.findById(pageId);
	}
	
	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, PageDTO pageDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = categoryService.findByUrlPathAndActive(pageDTO.getCategoryUrlPath(), true);
		if(categoryOptional.isPresent()) {
			try {
				Optional<Page> pageOptional = Optional.empty();
				var page = toPage(pageOptional, pageDTO);
				page.setCategoryUrlPath(pageDTO.getCategoryUrlPath());
				page.setActive(true);
				page = save(page);
				dataMap.put(DataConstants.PAGE, toPageDTO(page));
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_SAVED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PAGE_NOT_SAVED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_SAVED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, PageDTO pageDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = categoryService.findByUrlPathAndActive(pageDTO.getCategoryUrlPath(), true);
		if(categoryOptional.isPresent()) {
			try {
				Optional<Page> pageOptional = findByPageId(pageDTO.getPageId());
				if(pageOptional.isPresent()) {
					var page = toPage(pageOptional, pageDTO);
					page = save(page);
					dataMap.put(DataConstants.PAGE, toPageDTO(page));
					return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_UPDATED);
				}else {
					return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_FOUND);
				}
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PAGE_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, PageDTO pageDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = categoryService.findByUrlPathAndActive(pageDTO.getCategoryUrlPath(), true);
		if(categoryOptional.isPresent()) {
			try {
				Optional<Page> pageOptional = findByPageId(pageDTO.getPageId());
				if(pageOptional.isPresent()) {
					var page = toPage(pageOptional, pageDTO);
					page.setActive(false);
					save(page);
					return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_DELETED);
				}else {
					return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_FOUND);
				}
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PAGE_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getCategoryPageLinks(HttpServletRequest request, String categoryUrlPath) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = categoryService.findByUrlPathAndActive(categoryUrlPath, true);		
		if(categoryOptional.isPresent()) {
			Set<PageLinkDTO> categoryPageLinks = findCategoryPages(categoryUrlPath, true);
			List<PageLinkDTO> sortedList = categoryPageLinks.stream().sorted(Comparator.comparingInt(PageLinkDTO::getIndex)).collect(Collectors.toList());
			dataMap.put(DataConstants.PAGE_LINK_LIST, sortedList);
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_PAGE_LINKS_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getCategoryPage(HttpServletRequest request, String categoryUrlPath,
			String pageUrlPath) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = categoryService.findByUrlPathAndActive(categoryUrlPath, true);		
		if(categoryOptional.isPresent()) {			
			Optional<Page> pageOptional = findByCategoryUrlPathAndUrlPathAndActive(categoryUrlPath, pageUrlPath, true);
			if(pageOptional.isPresent()){
				dataMap.put(DataConstants.PAGE, toPageDTO(pageOptional.get()));
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_FOUND);	
			}else {
				return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_NOT_FOUND);	
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> updatePageLinks(HttpServletRequest request, List<PageLinkDTO> pageLinks) {
		Map<String, Object> dataMap = new HashMap<>();
		if(null != pageLinks) {
			pageRepository.saveAll(getOrderedPageLinks(pageLinks));
			return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_LINKS_UPDATED);	
		}
		return responseUtil.generateResponse(dataMap, ResponseConstants.PAGE_LINKS_NOT_UPDATED);
	}

	private List<Page> getOrderedPageLinks(List<PageLinkDTO> pageLinks) {
		List<Page> pages = new ArrayList<>();
		var index = 0;
		for(PageLinkDTO pageLink: pageLinks) {
			Optional<Page> pageOptional = findByPageId(pageLink.get_id());
			if(pageOptional.isPresent()) {
				var existingPage =  pageOptional.get();
				existingPage.setIndex(index);
				pages.add(existingPage);
				index++;
			}
		}
		return pages;
	}

}
