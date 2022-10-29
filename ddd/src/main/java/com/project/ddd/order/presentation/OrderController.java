package com.project.ddd.order.presentation;


import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.application.dto.OrderDetailDto;
import com.project.ddd.order.root.OrderService;
import com.project.ddd.order.value.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
