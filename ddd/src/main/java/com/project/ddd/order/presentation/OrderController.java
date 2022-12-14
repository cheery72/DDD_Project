package com.project.ddd.order.presentation;


import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.application.dto.OrderDetailDto;
import com.project.ddd.order.root.OrderService;
import com.project.ddd.order.value.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object> orderCreate(@RequestBody OrderCreateDto orderCreateDto){
        log.info("order create start ----");

        orderService.createOrder(orderCreateDto);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("{orderId}/detail")
    public ResponseEntity<OrderDetailDto> orderDetailFind(@PathVariable String orderId){
        log.info("order detail start ----");

        return ResponseEntity
                .ok(orderService.findOrderDetail(orderId));
    }

    @GetMapping("{memberId}/order-member")
    public ResponseEntity<Page<OrderDetailDto>> orderUserOrders(@PageableDefault(size = 4, sort = "createDate",
                                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                                                @PathVariable String memberId){
        log.info("user order find orders start ----");

        return ResponseEntity
                .ok(orderService.findUserOrders(pageable, memberId));
    }

}
