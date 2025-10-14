package com.sourav.ecommerce.mono.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>{

	List<Cart> findByCustomerId(int customerId);
	Optional<Cart> findByCustomerIdAndProductId(int customerId, int productId);
	void deleteByCustomerId(int customerId);
}
