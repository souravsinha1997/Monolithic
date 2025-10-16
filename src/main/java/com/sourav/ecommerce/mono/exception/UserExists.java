package com.sourav.ecommerce.mono.exception;

public class UserExists extends RuntimeException {

	public UserExists(String message) {
		super(message);
	}
}
