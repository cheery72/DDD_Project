package com.project.ddd.order.root;

import com.project.ddd.order.value.OrderId;
import com.project.ddd.order.value.Orderer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
    List<Order> findByOrderer(Orderer orderer);

    Page<Order> findPageByOrderer(Pageable pageable, Orderer orderer);

}
