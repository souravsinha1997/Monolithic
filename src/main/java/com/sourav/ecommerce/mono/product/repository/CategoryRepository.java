package com.sourav.ecommerce.mono.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.product.entity.Category;


public interface CategoryRepository extends JpaRepository<Category,Integer>{

	Category findByName(String category);

}
