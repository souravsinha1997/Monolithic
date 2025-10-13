package com.sourav.ecommerce.mono.exception;

public class InvalidLoginException extends RuntimeException{

	public InvalidLoginException(String message) {
		super(message);
	}
}
