package com.project.ddd.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {

    private String memberId;

    private String boardId;

    private String city;

    private String gu;

    private String dong;

    private String detail;

    private String requestMessage;

    private String receiverName;

    private String receiverPhone;

    private int quantity;
}
