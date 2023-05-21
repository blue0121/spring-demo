package io.jutil.spring.demo.jpa.entity;

import org.junit.jupiter.api.Assertions;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
public class OrderEntityTest {



	public static OrderEntity createOrder(Integer userId) {
		var entity = new OrderEntity();
		entity.setUserId(userId);
		return entity;
	}

	public static void verifyOrder(OrderEntity entity, Integer userId, Integer price) {
		Assertions.assertNotNull(entity);
		Assertions.assertEquals(userId, entity.getUserId());
		Assertions.assertEquals(price, entity.getPrice());
	}

	public static OrderItem createOrderItem(int productId, int price, int quantity) {
		var item = new OrderItem();
		item.setProductId(productId);
		item.setPrice(price);
		item.setQuantity(quantity);
		return item;
	}

	public static void verifyOrderItem(OrderItem item, int productId, int price, int quantity) {
		Assertions.assertNotNull(item);
		Assertions.assertEquals(productId, item.getProductId());
		Assertions.assertEquals(price, item.getPrice());
		Assertions.assertEquals(quantity, item.getQuantity());
	}
}
