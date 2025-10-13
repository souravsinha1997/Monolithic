package com.sourav.ecommerce.mono.product.dto;

import java.util.List;


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
public class CategoryResponse {

	private String name;
	private String description;
	
}
