package com.sourav.ecommerce.mono.product.service;

import java.util.List;

import com.sourav.ecommerce.mono.product.dto.ProductRequest;
import com.sourav.ecommerce.mono.product.dto.ProductResponse;

public interface ProductService {
	List<ProductResponse> getAllProducts();
	ProductResponse getProduct(int id);
	ProductResponse getProductByName(String name);
	List<ProductResponse> getProductByCategory(String category);
	String createProduct(ProductRequest request);
	String deleteProduct(int id);
	ProductResponse updateProduct(ProductRequest request, int id);
	
}
