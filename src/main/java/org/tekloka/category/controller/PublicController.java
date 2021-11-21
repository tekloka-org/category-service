package org.tekloka.category.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.category.service.CategoryService;
import org.tekloka.category.service.PageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

	private final CategoryService categoryService;
	private final PageService pageService;
	
	public PublicController(CategoryService categoryService, PageService pageService) {
		this.categoryService = categoryService;
		this.pageService = pageService;
	}
	
	
	@Operation(summary = "Get all categories")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category list found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "/get-all-categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActiveCategories(HttpServletRequest request) {
		return categoryService.getAllActiveCategories(request);
	}
	
	@Operation(summary = "Get all category page links")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category page links found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "{categoryUrlPath}/get-category-page-links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCategoryPageLinks(HttpServletRequest request, @PathVariable String categoryUrlPath) {
		 return pageService.getCategoryPageLinks(request, categoryUrlPath);
	}
	
	@Operation(summary = "Get all category pages")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category pages found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-category-page/{categoryUrlPath}/{pageUrlPath}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCategoryPage(HttpServletRequest request, @PathVariable String categoryUrlPath,
			@PathVariable String pageUrlPath) {
		 return pageService.getCategoryPage(request, categoryUrlPath, pageUrlPath);
	}
	
}
