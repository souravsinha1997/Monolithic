package com.sourav.ecommerce.mono.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductRequest {

	private String name;
	private String brand;
	private double price;
	private int quantity;
	private String category;
	
}
