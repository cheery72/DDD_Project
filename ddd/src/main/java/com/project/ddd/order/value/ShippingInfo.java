package com.project.ddd.order.value;

import com.project.ddd.common.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "metropolitanCity", column = @Column(name = "shipping_metropolitan_city")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "gu", column = @Column(name = "shipping_gu")),
            @AttributeOverride(name = "dong", column = @Column(name = "shipping_dong")),
            @AttributeOverride(name = "detail", column = @Column(name = "shipping_detail"))
    })
    private Address address;

    @Column(name = "shipping_message")
    private String message;

    @Embedded
    private Receiver receiver;

    public ShippingInfo(Address address, String message, Receiver receiver) {
        this.address = address;
        this.message = message;
        this.receiver = receiver;
    }

}
