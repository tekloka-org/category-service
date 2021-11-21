package org.tekloka.category.dto.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.tekloka.category.document.Category;
import org.tekloka.category.dto.CategoryDTO;

@Component
public class CategoryMapper {
	
	public Category toCategory(Optional<Category> categoryOptional, CategoryDTO categoryDTO) {
		var category = new Category();
		if(categoryOptional.isPresent()) {
			category = categoryOptional.get();
		}
		category.setName(categoryDTO.getName());
		category.setUrlPath(categoryDTO.getUrlPath());
		return category;
	}
	
	public CategoryDTO toCategoryDTO(Category category) {
		var categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryId(category.getCategoryId());
		categoryDTO.setName(category.getName());
		categoryDTO.setUrlPath(category.getUrlPath());
		return categoryDTO;
	}
	
	public Set<CategoryDTO> toCategoryDTOSet(Set<Category> categories){
		return categories.stream().map(this::toCategoryDTO).collect(Collectors.toSet());
	}

}
