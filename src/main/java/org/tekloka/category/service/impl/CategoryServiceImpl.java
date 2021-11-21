package org.tekloka.category.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.category.constants.AppConstants;
import org.tekloka.category.constants.DataConstants;
import org.tekloka.category.constants.ResponseConstants;
import org.tekloka.category.document.Category;
import org.tekloka.category.dto.CategoryDTO;
import org.tekloka.category.dto.mapper.CategoryMapper;
import org.tekloka.category.repository.CategoryRepository;
import org.tekloka.category.service.CategoryService;
import org.tekloka.category.util.ResponseUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final ResponseUtil responseUtil;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
			ResponseUtil responseUtil) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
		this.responseUtil = responseUtil;
	}
	
	public Category toCategory(Optional<Category> categoryOptional, CategoryDTO categoryDTO) {
		return categoryMapper.toCategory(categoryOptional, categoryDTO);
	}
	
	public CategoryDTO toCategoryDTO(Category category) {
		return categoryMapper.toCategoryDTO(category);
	}
	
	public Set<CategoryDTO> toCategoryDTOSet(Set<Category> categories){
		return categoryMapper.toCategoryDTOSet(categories);
	}
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public Set<Category> findByActive(boolean active){
		return categoryRepository.findByActive(active);
	}
	
	public Optional<Category> findByCategoryId(String categoryId){
		return categoryRepository.findById(categoryId);
	}
	
	@Override
	public Optional<Category> findByUrlPathAndActive(String urlPath, boolean active){
		return categoryRepository.findByUrlPathAndActive(urlPath, active);
	}
	
	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, CategoryDTO categoryDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Category> categoryOptional = Optional.empty();
			var category = toCategory(categoryOptional, categoryDTO);
			category.setActive(true);
			category = save(category);
			dataMap.put(DataConstants.CATEGORY, toCategoryDTO(category));
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_SAVED);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.CATEGORY_NOT_SAVED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_SAVED);
		}
	}

	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, CategoryDTO categoryDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = findByCategoryId(categoryDTO.getCategoryId());		
		if(categoryOptional.isPresent()) {
			try {
				var category = toCategory(categoryOptional, categoryDTO);
				category = save(category);
				dataMap.put(DataConstants.CATEGORY, toCategoryDTO(category));
				return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_UPDATED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.CATEGORY_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, CategoryDTO categoryDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Category> categoryOptional = findByCategoryId(categoryDTO.getCategoryId());		
		if(categoryOptional.isPresent()) {
			try {
				var category = categoryOptional.get();
				category.setActive(false);
				save(category);
				return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_DELETED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.CATEGORY_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getAllActiveCategories(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		Set<Category> categories = findByActive(true);
		dataMap.put(DataConstants.CATEGORY_LIST, toCategoryDTOSet(categories));
		return responseUtil.generateResponse(dataMap, ResponseConstants.CATEGORY_LIST_FOUND);
	}
	
}
