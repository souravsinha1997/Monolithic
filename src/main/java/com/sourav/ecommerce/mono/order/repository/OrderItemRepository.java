package com.sourav.ecommerce.mono.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{

}
