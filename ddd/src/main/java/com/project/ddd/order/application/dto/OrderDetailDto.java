package com.project.ddd.order.application.dto;

import com.project.ddd.order.root.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {

    private String orderId;

    private String memberId;

    private String city;

    private String gu;

    private String dong;

    private String addressDetail;

    private String requestMessage;

    private String receiverName;

    private String receiverPhone;

    private int amount;

    private String orderStatus;

    public static OrderDetailDto orderDetailDtoBuilder(Order order){
        return OrderDetailDto.builder()
                .orderId(order.getId().getId())
                .memberId(order.getOrderer().getMemberId().getId())
                .city(order.getShippingInfo().getAddress().getCity())
                .gu(order.getShippingInfo().getAddress().getGu())
                .dong(order.getShippingInfo().getAddress().getDong())
                .addressDetail(order.getShippingInfo().getAddress().getDetail())
                .requestMessage(order.getShippingInfo().getMessage())
                .receiverName(order.getShippingInfo().getReceiver().getName())
                .receiverPhone(order.getShippingInfo().getReceiver().getPhone())
                .amount(order.getTotalAmounts().getValue())
                .orderStatus(order.getOrderStatus().toString())
                .build();
    }

}
