package io.jutil.spring.demo.jpa.service;

import io.jutil.spring.demo.jpa.Application;
import io.jutil.spring.demo.jpa.entity.OrderEntityTest;
import io.jutil.spring.demo.jpa.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
@SpringBootTest(classes = Application.class)
class OrderServiceTest {
	@Autowired
	OrderRepository repository;

	@Autowired
	OrderService orderService;

	@BeforeEach
	void beforeEach() {
		repository.deleteAllInBatch();
	}

	@Test
	void testAdd() {
		var order = OrderEntityTest.createOrder(1);
		var item1 = OrderEntityTest.createOrderItem(1, 1000, 1);
		var item2 = OrderEntityTest.createOrderItem(2, 100, 10);
		var rs = orderService.add(order, List.of(item1, item2));
		Assertions.assertTrue(order.getId() != null);
		Assertions.assertTrue(rs.getId() != null);

		var view = orderService.get(rs.getId());
		OrderEntityTest.verifyOrder(view, 1, 2000);
		var items = view.getItems();
		Assertions.assertEquals(2, items.size());
		OrderEntityTest.verifyOrderItem(items.get(0), 1, 1000, 1);
		OrderEntityTest.verifyOrderItem(items.get(1), 2, 100, 10);
	}
}
