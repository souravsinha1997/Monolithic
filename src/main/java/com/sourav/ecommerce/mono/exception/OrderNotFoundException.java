package com.sourav.ecommerce.mono.exception;

public class OrderNotFoundException extends RuntimeException{

	public OrderNotFoundException(String message) {
		super(message);
	}
}
