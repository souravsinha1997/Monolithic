package com.sourav.ecommerce.mono.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sourav.ecommerce.mono.cart.dto.CartResponse;
import com.sourav.ecommerce.mono.cart.service.CartService;
import com.sourav.ecommerce.mono.exception.ClientDownException;
import com.sourav.ecommerce.mono.exception.EmptyCartException;
import com.sourav.ecommerce.mono.exception.InvalidRequestException;
import com.sourav.ecommerce.mono.exception.OrderNotFoundException;
import com.sourav.ecommerce.mono.order.dto.OrderItemResponse;
import com.sourav.ecommerce.mono.order.dto.OrderResponse;
import com.sourav.ecommerce.mono.order.entity.Order;
import com.sourav.ecommerce.mono.order.entity.OrderItem;
import com.sourav.ecommerce.mono.order.repository.OrderRepository;
import com.sourav.ecommerce.mono.product.dto.ProductResponse;
import com.sourav.ecommerce.mono.product.service.ProductService;
import com.sourav.ecommerce.mono.security.ValidateRequest;
import com.sourav.ecommerce.mono.user.dto.UserResponse;
import com.sourav.ecommerce.mono.user.service.UserService;





@Service
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepo;
	private final ProductService productService;
	private final UserService userService;
	private final CartService cartService;
	private final ValidateRequest validate;
	
	public OrderServiceImpl(OrderRepository orderRepo,ProductService productService,
			UserService userService,CartService cartService,ValidateRequest validate) {
		this.orderRepo = orderRepo;
		this.productService = productService;
		this.cartService = cartService;
		this.userService = userService;
		this.validate = validate;
	}
	
	@Transactional
	public String placeOrder(int customerId) {
		
		if(!validate.validateCustomer(customerId)) {
			throw new InvalidRequestException("Invalid token");
		}
		CartResponse cart = cartService.getAllCartItems(customerId);
		if(cart.getItems().isEmpty()) {
			throw new EmptyCartException("Cart is empty, please add items in your cart");
		}
		
		//ResponseEntity<UserResponse> user = customerClient.getUser(customerId);
		
		
		double totalAmount = cart.getItems().stream()
			    .mapToDouble(item -> item.getPrice() * item.getQuantity())
			    .sum();
		
		Order order = new Order();
		order.setCustomerId(customerId); 
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("ORDER PLACED");
		order.setTotalPrice(BigDecimal.valueOf(totalAmount));
		
		List<OrderItem> orderItems = new ArrayList<>();
		for(ProductResponse item : cart.getItems()) {
			//ResponseEntity<ProductResponse> product = productClient.getProductById(item.getProductId());
			OrderItem orderItem = new OrderItem();
			int productId = productService.getProductIdByProductName(item.getName());
			orderItem.setProductId(productId);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setUnitPrice(BigDecimal.valueOf(item.getPrice()));
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		
		order.setOrderItems(orderItems);
		Order savedOrder = orderRepo.save(order);
		String clearResponse = cartService.clearCartItems(customerId);
		if(clearResponse.equals("Fallback")) {
			throw new ClientDownException("Cart service is down, please try after sometime");
		}
		
		return "Order placed with Order ID : "+savedOrder.getId();
	}
	
	@Transactional
	public OrderResponse trackOrder(int id) {
		Order order = orderRepo.findById(id).orElseThrow(()-> new OrderNotFoundException("Invalid order id : "+id));
		if(!validate.validateCustomer(order.getCustomerId())) {
			throw new InvalidRequestException("Invalid token");
		}
		//get customer details using customer client
		UserResponse customer = userService.getUser(order.getCustomerId());
		
		String customerName = customer.getUserName();
		OrderResponse response = new OrderResponse();
		
		response.setCustomerName(customerName);
		response.setOrderDate(order.getOrderDate());
		response.setTotalPrice(order.getTotalPrice());
		response.setOrderId(id);
		response.setStatus(order.getStatus());
		
		List<OrderItemResponse> itemResponse = new ArrayList<>();
		List<OrderItem> items = order.getOrderItems();
		
		for(OrderItem item : items) {
			ProductResponse product = productService.getProduct(item.getProductId());
			OrderItemResponse orderItemResponse = new OrderItemResponse();
			orderItemResponse.setPrice(item.getUnitPrice());
			orderItemResponse.setProductName(product.getName());
			orderItemResponse.setQuantity(item.getQuantity());
			
			itemResponse.add(orderItemResponse);
		}
		
		response.setItems(itemResponse);
		return response;
	}

	
	@Transactional
	public String cancelOrder(int orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Invalid Order Number :"+orderId));		
		order.setStatus("CANCELLED");		
		Order canceledOrder = orderRepo.save(order);
				
		return "Order canceled for the order id : "+canceledOrder.getId();
	}
}
