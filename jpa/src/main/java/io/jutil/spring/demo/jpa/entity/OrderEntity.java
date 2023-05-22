package io.jutil.spring.demo.jpa.entity;

import io.jutil.spring.demo.jpa.util.DateUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "od_order")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
	@SequenceGenerator(name = "id_seq", sequenceName = "od_order_id_seq", allocationSize = 1)
	@Column(name = "id", columnDefinition = "int4", nullable = false)
	private Integer id;

	@Column(name = "user_id", columnDefinition = "int4", nullable = false)
	private Integer userId;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "items", columnDefinition = "json", nullable = false)
	private List<OrderItem> items;

	@Column(name = "price", columnDefinition = "int4", nullable = false)
	private Integer price;

	@Column(name = "created_at", columnDefinition = "timestamp(0)", nullable = false)
	private Instant createdAt;

	@PrePersist
	public void onPrePersist() {
		this.createdAt = DateUtil.now();
		this.price = 0;

		if (items != null && !items.isEmpty()) {
			for (var item : items) {
				price += item.getPrice() * item.getQuantity();
			}
		}
	}
}
