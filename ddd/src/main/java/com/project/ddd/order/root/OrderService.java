package com.project.ddd.order.root;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.common.exception.NoSuchBoardException;
import com.project.ddd.member.value.MemberId;
import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.application.dto.OrderDetailDto;
import com.project.ddd.order.value.OrderId;
import com.project.ddd.order.value.Orderer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createOrder(OrderCreateDto orderCreateDto){
        Board board = boardRepository.findById(BoardId.of(orderCreateDto.getBoardId()))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글이 없습니다."));

        orderRepository.save(Order.createOrder(orderCreateDto, board.getPrice()));
    }

    public OrderDetailDto findOrderDetail(String orderId){
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new NoSuchElementException("요청한 주문이 없습니다."));

        return OrderDetailDto.orderDetailDtoBuilder(order);
    }

    public Page<OrderDetailDto> findUserOrders(Pageable pageable, String memberId){
        Page<Order> orders = orderRepository.findPageByOrderer(pageable, Orderer.of(MemberId.of(memberId)));

        return new PageImpl<>(OrderDetailDto.orderListDetailDtoBuilder(orders),pageable,orders.getTotalElements());

    }
    
    @Transactional
    public void cancelOrder(String orderId){
        Order order = orderRepository.findById(OrderId.of(orderId))
                .orElseThrow(() -> new NoSuchElementException("요청한 주문이 없습니다."));

        order.changeOrderStatus();
    }
}
