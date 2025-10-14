package com.sourav.ecommerce.mono.exception;

public class EmptyCartException extends RuntimeException {

	public EmptyCartException(String message) {
		super(message);
	}
}
