package com.project.ddd.order.root;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.common.Address;
import com.project.ddd.common.BaseTime;
import com.project.ddd.member.value.MemberId;
import com.project.ddd.order.application.dto.OrderCreateDto;
import com.project.ddd.order.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "purchase_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseTime {

    @EmbeddedId
    private OrderId id;

    @Embedded
    private Orderer orderer;

    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "order_line_idx")
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> orderLines;

    @Embedded
    private ShippingInfo shippingInfo;

    @Embedded
    private Money totalAmounts;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    public void changeOrderStatus(){
        this.orderStatus = OrderStatus.CANCELED;
    }

    public static Order createOrder(OrderCreateDto orderCreateDto, int boardPrice){

        int orderPrice = checkOrderQuantity(orderCreateDto.getQuantity(),boardPrice);

        return Order.builder()
                .id(OrderId.createOrderId())
                .orderer(Orderer.of(MemberId.of(orderCreateDto.getMemberId())))
                .orderLines(List.of(OrderLine.of(BoardId.of(orderCreateDto.getBoardId()),orderCreateDto.getQuantity(),orderPrice)))
                .shippingInfo(ShippingInfo.of(
                        Address.of(orderCreateDto.getCity(),orderCreateDto.getGu(),orderCreateDto.getDong(),orderCreateDto.getDetail()),
                        orderCreateDto.getRequestMessage(),Receiver.of(orderCreateDto.getReceiverName(),orderCreateDto.getReceiverPhone())))
                .totalAmounts(Money.of(orderPrice))
                .orderStatus(OrderStatus.PAYMENT_WAITING)
                .build();
    }

    private static int checkOrderQuantity(int orderQuantity, int boardPrice){
        return orderQuantity*boardPrice;
    }

    @Builder
    public Order(OrderId id, Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, Money totalAmounts, OrderStatus orderStatus) {
        this.id = id;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.shippingInfo = shippingInfo;
        this.totalAmounts = totalAmounts;
        this.orderStatus = orderStatus;
    }
}
