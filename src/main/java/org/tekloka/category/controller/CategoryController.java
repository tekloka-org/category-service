package org.tekloka.category.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.category.dto.CategoryDTO;
import org.tekloka.category.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	
	@Operation(summary = "Add Category")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody CategoryDTO categoryDTO) {
		return categoryService.save(request, categoryDTO);
	}
	
	@Operation(summary = "Update Category")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody CategoryDTO categoryDTO) {
		return categoryService.update(request, categoryDTO);
	}
	
	@Operation(summary = "Delete Category")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody CategoryDTO categoryDTO) {
		return categoryService.delete(request, categoryDTO);
	}

	
}
