package com.project.ddd.order.root;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.common.exception.NoSuchBoardException;
import com.project.ddd.order.application.dto.OrderCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Order createOrder(OrderCreateDto orderCreateDto){
        Board board = boardRepository.findById(BoardId.of(orderCreateDto.getBoardId()))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글이 없습니다."));

        return orderRepository.save(Order.createOrder(orderCreateDto, board.getPrice()));
    }
}
