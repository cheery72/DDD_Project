package com.project.ddd.order.value;


import com.project.ddd.board.value.BoardId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class OrderId implements Serializable {

    @Column(name = "order_id")
    private String id;

    public OrderId(String id) {
        this.id = id;
    }

    public static OrderId createOrderId() {
        return new OrderId(UUID.randomUUID().toString().replace("-",""));
    }


    public static OrderId of(String id){
        return new OrderId(id);
    }
}
