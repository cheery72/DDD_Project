package com.project.ddd.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.application.dto.OrderDetailDto;
import com.project.ddd.order.root.Order;
import com.project.ddd.order.root.OrderRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Mock
    private OrderRepository orderRepository;

    private final String BASE_URL = "/order";

    @BeforeEach()
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("주문 생성")
    public void createOrder() throws Exception {
        String body = objectMapper.writeValueAsString(
                OrderCreateDto.builder()
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
                        .build()
        );

        mockMvc.perform(post(BASE_URL + "/create")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("주문 상세 조회")
    public void findDetailOrder() throws Exception {
        String orderId = "45cc2e51ca184286af3d5bc931f65c09";
        OrderDetailDto orderDetailDto =
                OrderDetailDto.builder()
                        .orderId("45cc2e51ca184286af3d5bc931f65c09")
                        .memberId("user1")
                        .city("광주광역시")
                        .gu("북구")
                        .dong("각화동")
                        .addressDetail("서희")
                        .requestMessage("message")
                        .receiverName("kim")
                        .receiverPhone("010")
                        .amount(50000)
                        .orderStatus("PAYMENT_WAITING")
                        .build();

        mockMvc.perform(get(BASE_URL + "/" + orderId + "/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("order_id").value(Matchers.equalTo("45cc2e51ca184286af3d5bc931f65c09")))
                .andExpect(jsonPath("member_id").value(Matchers.equalTo("user1")))
                .andExpect(jsonPath("city").value(Matchers.equalTo("광주광역시")))
                .andExpect(jsonPath("gu").value(Matchers.equalTo("북구")))
                .andExpect(jsonPath("dong").value(Matchers.equalTo("각화동")))
                .andExpect(jsonPath("address_detail").value(Matchers.equalTo("서희")))
                .andExpect(jsonPath("request_message").value(Matchers.equalTo("message")))
                .andExpect(jsonPath("receiver_name").value(Matchers.equalTo("kim")))
                .andExpect(jsonPath("receiver_phone").value(Matchers.equalTo("010")))
                .andExpect(jsonPath("amount").value(Matchers.equalTo(50000)))
                .andExpect(jsonPath("order_status").value(Matchers.equalTo("PAYMENT_WAITING")));
    }

    @Test
    @DisplayName("유저 주문 페이징 조회")
    public void findUserOrdersPage() throws Exception {
        String memberId = "user1";

        OrderDetailDto orderDetailDto =
                OrderDetailDto.builder()
                        .orderId("45cc2e51ca184286af3d5bc931f65c09")
                        .memberId("user1")
                        .city("광주광역시")
                        .gu("북구")
                        .dong("각화동")
                        .addressDetail("서희")
                        .requestMessage("message")
                        .receiverName("kim")
                        .receiverPhone("010")
                        .amount(50000)
                        .orderStatus("PAYMENT_WAITING")
                        .build();

        mockMvc.perform(get(BASE_URL + "/" + memberId + "/order-member")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "createDate,desc"))
                .andExpect(status().isOk());
    }
}