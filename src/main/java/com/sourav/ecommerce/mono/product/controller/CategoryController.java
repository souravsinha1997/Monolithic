package com.sourav.ecommerce.mono.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.product.dto.CategoryResponse;
import com.sourav.ecommerce.mono.product.service.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategory(@PathVariable int id){
		return ResponseEntity.ok(categoryService.getCategory(id));
	}
}
