package com.sourav.ecommerce.mono.cart.service;

import com.sourav.ecommerce.mono.cart.dto.CartRequest;
import com.sourav.ecommerce.mono.cart.dto.CartResponse;

public interface CartService {

	CartResponse getAllCartItems(int customerId);
	String addItems(int customerId,CartRequest request);
	CartResponse getCartItem(int customerId,CartRequest request);
	String updateCartItem(int customerId,CartRequest request);
	String removeCartItem(int customerId,CartRequest request);
	String clearCartItems(int customerId);
}
