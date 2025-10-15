package com.sourav.ecommerce.mono.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.dto.MessageResponse;
import com.sourav.ecommerce.mono.order.dto.OrderResponse;
import com.sourav.ecommerce.mono.order.service.OrderService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> trackOrder(@PathVariable int orderId){
		return ResponseEntity.ok(orderService.trackOrder(orderId));
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/{customerId}")
	public ResponseEntity<MessageResponse> placeOrder(@PathVariable int customerId){
		return ResponseEntity.ok(new MessageResponse(orderService.placeOrder(customerId)));
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/{orderId}")
	public ResponseEntity<MessageResponse> cancelOrder(@PathVariable int orderId){
		return ResponseEntity.ok(new MessageResponse(orderService.cancelOrder(orderId)));
	}
}
