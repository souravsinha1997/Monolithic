package com.sourav.ecommerce.mono.product.service;

import java.util.List;

import com.sourav.ecommerce.mono.product.dto.CategoryRequest;
import com.sourav.ecommerce.mono.product.dto.CategoryResponse;


public interface CategoryService {
	List<CategoryResponse> getAllCategories();
	CategoryResponse getCategory(int id);
	String saveCategory(CategoryRequest request);
	String deleteCategory(int id);
	CategoryResponse updateCategory(CategoryRequest request,int id);
}
