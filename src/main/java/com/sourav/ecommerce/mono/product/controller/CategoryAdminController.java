package com.sourav.ecommerce.mono.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.product.dto.CategoryRequest;
import com.sourav.ecommerce.mono.product.dto.CategoryResponse;
import com.sourav.ecommerce.mono.dto.MessageResponse;
import com.sourav.ecommerce.mono.product.service.CategoryService;


@RestController
@RequestMapping("/api/admin")
public class CategoryAdminController {

	private final CategoryService categoryService;
	
	public CategoryAdminController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PreAuthorize("hasAuthority('ADMIN")
	@PostMapping("/categories")
	public ResponseEntity<MessageResponse> createCategory(@RequestBody CategoryRequest request){
		return ResponseEntity.ok(new MessageResponse(categoryService.saveCategory(request)));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<MessageResponse> deleteCategory(@PathVariable int id){
		return ResponseEntity.ok(new MessageResponse(categoryService.deleteCategory(id)));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest request,@PathVariable int id){
		return ResponseEntity.ok(categoryService.updateCategory(request, id));
	}
}
