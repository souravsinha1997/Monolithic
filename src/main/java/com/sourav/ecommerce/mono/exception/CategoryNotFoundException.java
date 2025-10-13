package com.sourav.ecommerce.mono.exception;

public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException(String message) {
		super(message);
	}
}
