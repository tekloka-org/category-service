package org.tekloka.category.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.tekloka.category.document.Category;
import org.tekloka.category.dto.CategoryDTO;

public interface CategoryService {

	ResponseEntity<Object> save(HttpServletRequest request, CategoryDTO categoryDTO);

	ResponseEntity<Object> getAllActiveCategories(HttpServletRequest request);

	Optional<Category> findByUrlPathAndActive(String urlPath, boolean active);

	ResponseEntity<Object> update(HttpServletRequest request, CategoryDTO categoryDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, CategoryDTO categoryDTO);

}
