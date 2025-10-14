package com.sourav.ecommerce.mono.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourav.ecommerce.mono.cart.dto.CartRequest;
import com.sourav.ecommerce.mono.cart.dto.CartResponse;
import com.sourav.ecommerce.mono.cart.entity.Cart;
import com.sourav.ecommerce.mono.cart.repository.CartRepository;
import com.sourav.ecommerce.mono.exception.EmptyCartException;
import com.sourav.ecommerce.mono.exception.InvalidQuantityException;
import com.sourav.ecommerce.mono.exception.InvalidRequestException;
import com.sourav.ecommerce.mono.product.dto.ProductResponse;
import com.sourav.ecommerce.mono.product.service.ProductService;
import com.sourav.ecommerce.mono.security.ValidateRequest;

@Service
public class CartServiceImpl implements CartService{

	
	private final CartRepository repository;
	private final ValidateRequest validate;
	private final ProductService productService;
	
	public CartServiceImpl(ProductService productService,ValidateRequest validate,CartRepository repository) {
		this.repository = repository;
		this.validate = validate;
		this.productService = productService;
	}
	
	public CartResponse getAllCartItems(int customerId) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		CartResponse response = new CartResponse();
		response.setCustomerId(customerId);
		
		List<Cart> cart = repository.findByCustomerId(customerId);
		if(cart.isEmpty()) {
			throw new EmptyCartException("Cart is empty");
		}
		
		List<ProductResponse> items = cart.stream()
		        .map(item -> {
		            // fetch product details
		            ProductResponse product = productService.getProduct(item.getProductId());
		            // override quantity with the quantity from the cart
		            product.setQuantity(item.getProductQuantity());
		            return product;
		        })
		        .toList();
		response.setItems(items);
		
		return response;	
	}
	
	public String addItems(int customerId,CartRequest request) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		ProductResponse product = productService.getProduct(request.getProductId());
		
		Cart cart = new Cart();
		System.out.println(customerId);
		System.out.println(request.toString());
		if(product.getQuantity()<request.getQuantity() || request.getQuantity()<=0) {
			throw new InvalidQuantityException("Invalid Quantity");
		}
		else {
			cart.setProductQuantity(request.getQuantity());
			cart.setProductId(request.getProductId());
			cart.setCustomerId(customerId);
		}

		repository.save(cart);
		
		return "Item added to the cart";
		
	}
	
	public CartResponse getCartItem(int customerId,CartRequest request) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		
		Optional<Cart> cart = repository.findByCustomerIdAndProductId(customerId, request.getProductId());
		if(cart.isEmpty()) {
			throw new EmptyCartException("Product is not there in the cart");
		}
		
		CartResponse response = new CartResponse();
		List<ProductResponse> itemList = cart.stream().map(x->productService.getProduct(x.getProductId())).toList();
		response.setCustomerId(customerId);
		response.setItems(itemList);
		
		return response;
	}
	
	public String updateCartItem(int customerId,CartRequest request) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		Optional<Cart> cart = repository.findByCustomerIdAndProductId(customerId, request.getProductId());
		if(cart.isEmpty()) {
			throw new EmptyCartException("Product is not there in the cart");
		}
		
		cart.get().setProductQuantity(request.getQuantity());
		repository.save(cart.get());
		
		return "Cart updated successfully";
	}
	
	@Transactional
	public String removeCartItem(int customerId,CartRequest request) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		Optional<Cart> item = repository.findByCustomerIdAndProductId(customerId, request.getProductId());
		repository.delete(item.get());
		return "Item removed successfully";
	}
	
	@Transactional
	public String clearCartItems(int customerId) {
		if(!validate.validateCustomer(customerId)) throw new InvalidRequestException("Invalid Token");
		repository.deleteByCustomerId(customerId);
		return "All items are cleared";
	}
}
