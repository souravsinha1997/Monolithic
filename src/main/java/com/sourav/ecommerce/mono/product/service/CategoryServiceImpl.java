package com.sourav.ecommerce.mono.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourav.ecommerce.mono.exception.CategoryNotFoundException;
import com.sourav.ecommerce.mono.product.dto.CategoryRequest;
import com.sourav.ecommerce.mono.product.dto.CategoryResponse;
import com.sourav.ecommerce.mono.product.entity.Category;
import com.sourav.ecommerce.mono.product.entity.Product;
import com.sourav.ecommerce.mono.product.repository.CategoryRepository;
import com.sourav.ecommerce.mono.product.repository.ProductRepository;



@Service
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;
	
	public CategoryServiceImpl(CategoryRepository categoryRepo,ProductRepository productRepo) {
		this.categoryRepo = categoryRepo;
		this.productRepo = productRepo;
	}
	
	public List<CategoryResponse> getAllCategories(){
		List<Category> categories = categoryRepo.findAll();
		
		if(categories==null) {
			throw new CategoryNotFoundException("No Category was found");
		}
		
		List<CategoryResponse> categoryResponse = new ArrayList<>();
		for(Category category : categories) {
			CategoryResponse response = new CategoryResponse();
			response.setName(category.getName());
			response.setDescription(category.getDescription());
			categoryResponse.add(response);
		}
		return categoryResponse;
	}
	
	public CategoryResponse getCategory(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category not found"));
		
		CategoryResponse response = new CategoryResponse();
		response.setName(category.getName());
		response.setDescription(category.getDescription());
		
		return response;
	}
	
	public String saveCategory(CategoryRequest request) {
		
		Category category = new Category();
		Category savedCategory = categoryRepo.findByName(request.getName());
		if(savedCategory!=null) throw new RuntimeException("Category with same name exist");
		category.setDescription(request.getDescription());
		category.setName(request.getName());
		categoryRepo.save(category);
		
		return "Category Saved Successfully";
	}
	
	@Transactional
	public String deleteCategory(int id) {
		Category savedCategory = categoryRepo.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category does not exist"));
		
		List<Product> products = productRepo.findByCategory(savedCategory);
		for(Product product : products) {
			product.setCategory(null);
		}
		
		categoryRepo.delete(savedCategory);
	
		return "Category Deleted Successfully";
	}
	
	public CategoryResponse updateCategory(CategoryRequest request,int id) {
		
		Category savedCategory = categoryRepo.findById(id)
				.orElseThrow(()-> new CategoryNotFoundException("Category does not exist"));
		
		if(request.getDescription()!=null && !request.getDescription().isEmpty())
			savedCategory.setDescription(request.getDescription());
		
		if(request.getName()!=null && !request.getName().isEmpty())
			savedCategory.setName(request.getName());
		
		Category updatedCategory = categoryRepo.save(savedCategory);
		
		CategoryResponse response = new CategoryResponse();
		response.setDescription(updatedCategory.getDescription());
		response.setName(updatedCategory.getName());
		
		return response;
		
	}
}
