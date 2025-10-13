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

import com.sourav.ecommerce.mono.dto.MessageResponse;
import com.sourav.ecommerce.mono.product.dto.ProductRequest;
import com.sourav.ecommerce.mono.product.dto.ProductResponse;
import com.sourav.ecommerce.mono.product.service.ProductService;


@RestController
@RequestMapping("/api/admin")
public class ProductAdminController {

	private final ProductService productService;
	
	public ProductAdminController(ProductService productService) {
		this.productService = productService;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/products")
	public ResponseEntity<MessageResponse> createProduct(@RequestBody ProductRequest request){
		return ResponseEntity.ok(new MessageResponse(productService.createProduct(request)));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/products/{id}")
	public ResponseEntity<MessageResponse> deleteProduct(@PathVariable int id){
		return ResponseEntity.ok(new MessageResponse(productService.deleteProduct(id)));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/products/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable int id){
		return ResponseEntity.ok(productService.updateProduct(request, id));
	}
}
