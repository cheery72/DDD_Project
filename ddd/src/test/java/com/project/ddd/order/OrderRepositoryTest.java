package com.project.ddd.order;

import com.project.ddd.board.value.BoardId;
import com.project.ddd.common.Address;
import com.project.ddd.member.value.MemberId;
import com.project.ddd.order.root.Order;
import com.project.ddd.order.root.OrderRepository;
import com.project.ddd.order.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private final Order order =
        new Order(OrderId.of("order1"), Orderer.of(MemberId.of("user1")),
            List.of(OrderLine.of(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"),0,0)),
            ShippingInfo.of(Address.of("광주광역시","북구","각화동","서희"),"message", Receiver.of("kim","010")),
            Money.of(0),OrderStatus.PAYMENT_WAITING);

    public void saveOrder(){
        Order order =
            new Order(OrderId.createOrderId(), Orderer.of(MemberId.of("user1")),
                    List.of(OrderLine.of(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"),0,0)),
                    ShippingInfo.of(Address.of("광주광역시","북구","각화동","서희"),"message", Receiver.of("kim","010")),
                    Money.of(0),OrderStatus.PAYMENT_WAITING);

        for (int i = 0; i < 5; i++) {
            orderRepository.save(order);
        }
    }

    @Test
    @DisplayName("주문 생성")
    public void createOrder(){
        Order saveOrder = orderRepository.save(order);

        assertThat(order.getId()).isEqualTo(saveOrder.getId());
    }

    @Test
    @DisplayName("주문 상세 조회")
    public void findOrder(){
        Optional<Order> byId = orderRepository.findById(order.getId());
        Order newOrder = byId.get();

        assertThat(order.getOrderer().getMemberId().getId()).isEqualTo("user1");
    }

    @Test
    @DisplayName("유저 주문 조회")
    public void findMemberOrders(){
        List<Order> orderList = orderRepository.findByOrderer(Orderer.of(MemberId.of("user1")));

        for (Order order: orderList){
            assertThat(order.getOrderer().getMemberId().getId()).isEqualTo("user1");
        }

    }

    @Test
    @DisplayName("주문 전체 취소")
    public void cancelOrder(){
        orderRepository.save(order);
        Optional<Order> byId = orderRepository.findById(order.getId());
        Order order = byId.get();

        orderRepository.delete(order);

        Optional<Order> deleteOrder = orderRepository.findById(order.getId());

        assertThat(deleteOrder).isEmpty();
    }
}