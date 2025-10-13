package com.sourav.ecommerce.mono.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.product.entity.Category;
import com.sourav.ecommerce.mono.product.entity.Product;


public interface ProductRepository extends JpaRepository<Product,Integer>{

	List<Product> findByCategory(Category category);
	Optional<Product> findByName(String name);

}
