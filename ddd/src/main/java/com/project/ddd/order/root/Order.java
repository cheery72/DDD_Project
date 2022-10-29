package com.project.ddd.order.root;

import com.project.ddd.common.BaseTime;
import com.project.ddd.order.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private OrderStatus status;

    @Builder
    public Order(OrderId id, Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, Money totalAmounts, OrderStatus status) {
        this.id = id;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.shippingInfo = shippingInfo;
        this.totalAmounts = totalAmounts;
        this.status = status;
    }
}
