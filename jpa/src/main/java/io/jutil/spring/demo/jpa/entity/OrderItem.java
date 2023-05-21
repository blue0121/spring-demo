package io.jutil.spring.demo.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
	private int productId;
	private int price;
	private int quantity;

}
