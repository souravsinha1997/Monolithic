package com.sourav.ecommerce.mono.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

}
