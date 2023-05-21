package io.jutil.spring.demo.jpa.service;

import io.jutil.spring.demo.jpa.entity.OrderEntity;
import io.jutil.spring.demo.jpa.entity.OrderItem;
import io.jutil.spring.demo.jpa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {
	@Autowired
	OrderRepository orderRepository;


	public OrderEntity add(OrderEntity order, List<OrderItem> items) {
		order.setItems(items);
		return orderRepository.save(order);
	}

	public OrderEntity get(Integer id) {
		return orderRepository.findById(id).orElse(null);
	}

	public List<OrderEntity> listAll() {
		return orderRepository.findAll();
	}

	public void delete(Integer id) {
		orderRepository.deleteById(id);
	}
}
