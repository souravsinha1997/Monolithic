package com.sourav.ecommerce.mono.cart.entity;

import com.sourav.ecommerce.mono.user.entity.Role;
import com.sourav.ecommerce.mono.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="customer_id", nullable = false)
	int customerId;
	
	@Column(name="product_id", nullable = false)
	int productId;
	
	@Column(name="product_quantity", nullable = false)
	int productQuantity;
}
