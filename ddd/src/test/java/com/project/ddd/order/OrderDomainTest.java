package com.project.ddd.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.root.BoardService;
import com.project.ddd.board.value.*;
import com.project.ddd.common.Address;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.root.Order;
import com.project.ddd.order.root.OrderRepository;
import com.project.ddd.order.root.OrderService;
import com.project.ddd.order.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
class OrderDomainTest {

    @Mock
    private Order order;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private OrderService orderService;

    private final Order saveOrder =
            new Order(OrderId.of("order1"), Orderer.of(MemberId.of("user1")),
                    List.of(OrderLine.of(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"),0,0)),
                    ShippingInfo.of(Address.of("광주광역시","북구","각화동","서희"),"message", Receiver.of("kim","010")),
                    Money.of(0),OrderStatus.PAYMENT_WAITING);

    public Order orderSave(){
        return new Order(OrderId.createOrderId(), Orderer.of(MemberId.of("user1")),
                    List.of(OrderLine.of(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"),0,0)),
                    ShippingInfo.of(Address.of("광주광역시","북구","각화동","서희"),"message", Receiver.of("kim","010")),
                    Money.of(0),OrderStatus.PAYMENT_WAITING);
    }

    public List<Order> saveOrderList(){
        Order order =
                new Order(OrderId.createOrderId(), Orderer.of(MemberId.of("user1")),
                        List.of(OrderLine.of(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"),0,0)),
                        ShippingInfo.of(Address.of("광주광역시","북구","각화동","서희"),"message", Receiver.of("kim","010")),
                        Money.of(0),OrderStatus.PAYMENT_WAITING);
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orders.add(order);
        }
        return orders;
    }

    private final Board board = new Board(BoardId.of("caf3a51d9a47401a99872a91c743bc9c"), new Boarder(MemberId.of("user1")),
            new BoardContent("새로운 게시글"), List.of(new BoardTag("#안녕")),
            Collections.emptyList(),Collections.emptyList(),0, Status.REGISTRATION,50);


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("주문 생성")
    public void createOrder() {
        OrderCreateDto orderCreateDto = OrderCreateDto.builder()
                .memberId("user1")
                .boardId("caf3a51d9a47401a99872a91c743bc9c")
                .city("광주광역시")
                .gu("북구")
                .dong("각화동")
                .detail("서희")
                .requestMessage("message")
                .receiverName("kim")
                .receiverPhone("010")
                .quantity(1)
                .build();

        Order order = orderSave();

        Order createOrder = Order.createOrder(orderCreateDto,board.getPrice());

        when(orderRepository.save(any()))
                .thenReturn(createOrder);
        Order newOrder = orderRepository.save(createOrder);

        assertThat(order.getOrderLines().get(0).getBoardId().getId()).isEqualTo(newOrder.getOrderLines().get(0).getBoardId().getId());

    }

    @Test
    @DisplayName("주문 상세 조회")
    public void findDetailOrder(){
        Order order = orderSave();

        when(orderRepository.findById(any()))
                .thenReturn(Optional.ofNullable(order));

        Optional<Order> byId = orderRepository.findById(Objects.requireNonNull(order).getId());
        Order newOrder = byId.get();

        assertThat(order).isEqualTo(newOrder);
    }

    @Test
    @DisplayName("유저 주문 조회")
    public void findMemberOrders(){
        String memberId = "user1";
        List<Order> orderList = saveOrderList();

        when(orderRepository.findByOrderer(any()))
                .thenReturn(orderList);

        List<Order> newOrders = orderRepository.findByOrderer(Orderer.of(MemberId.of(memberId)));

        assertThat(orderList).isEqualTo(newOrders);
    }

    @Test
    @DisplayName("주문 취소")
    public void deleteOrder(){

        String orderId = "order1";

        when(orderRepository.findById(any()))
                .thenReturn(Optional.of(saveOrder));

        Order findOrder = orderRepository.findById(OrderId.of(orderId)).get();

        findOrder.changeOrderStatus();

        assertThat(saveOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }
}