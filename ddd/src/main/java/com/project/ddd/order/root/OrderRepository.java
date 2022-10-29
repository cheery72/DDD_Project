package com.project.ddd.order.root;

import com.project.ddd.order.value.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
