package com.sourav.ecommerce.mono.cart.dto;

import java.util.List;

import com.sourav.ecommerce.mono.product.dto.ProductResponse;

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
public class CartResponse {

	private int customerId;
	private List<ProductResponse> items;
}
