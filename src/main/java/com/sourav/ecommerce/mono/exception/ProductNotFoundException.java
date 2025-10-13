package com.sourav.ecommerce.mono.exception;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String message) {
		super(message);
	}
}
