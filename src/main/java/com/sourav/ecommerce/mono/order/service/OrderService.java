package com.sourav.ecommerce.mono.order.service;

import com.sourav.ecommerce.mono.order.dto.OrderResponse;

public interface OrderService {
	String placeOrder(int customerId);
	OrderResponse trackOrder(int id);
	String cancelOrder(int orderId);
}
