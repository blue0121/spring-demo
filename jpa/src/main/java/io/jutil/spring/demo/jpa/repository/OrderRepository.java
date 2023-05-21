package io.jutil.spring.demo.jpa.repository;

import io.jutil.spring.demo.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
