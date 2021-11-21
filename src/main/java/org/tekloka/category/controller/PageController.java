package org.tekloka.category.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.category.dto.PageDTO;
import org.tekloka.category.dto.PageLinkDTO;
import org.tekloka.category.service.PageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/page")
public class PageController {

	private final PageService pageService;
	
	public PageController(PageService pageService) {
		this.pageService = pageService;
	}
	
	@Operation(summary = "Add Category page")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Categor page added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
		return pageService.save(request, pageDTO);
	}
	
	@Operation(summary = "Update Category page")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category page updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
		return pageService.update(request, pageDTO);
	}
	
	@Operation(summary = "Delete Category page")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Category page deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody PageDTO pageDTO) {
		return pageService.delete(request, pageDTO);
	}
	
	@Operation(summary = "Update Page Links")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Page Links successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update-page-links", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updatePageLinks(HttpServletRequest request, @RequestBody List<PageLinkDTO> pageLinks) {
		return pageService.updatePageLinks(request, pageLinks);
	}
	
}
