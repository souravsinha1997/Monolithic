package com.sourav.ecommerce.mono.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.product.dto.ProductResponse;
import com.sourav.ecommerce.mono.product.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	
//	@GetMapping
//	public ResponseEntity<List<ProductResponse>> getAllProducts(){
//		return ResponseEntity.ok(productService.getAllProducts());
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable int id){
		return ResponseEntity.ok(productService.getProduct(id));
		//throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Simulated failure from Product Service");
	}
	
	@GetMapping
	public ResponseEntity<?> getProductByName(@RequestParam(required = false) String name){
		if(name!=null && !name.isEmpty())
			return ResponseEntity.ok(productService.getProductByName(name));
		else 
			return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/categories/{category}")
	public ResponseEntity<List<ProductResponse>> getProduct(@PathVariable String category){
		return ResponseEntity.ok(productService.getProductByCategory(category));
	}
}
