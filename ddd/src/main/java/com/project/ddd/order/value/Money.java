package com.project.ddd.order.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Column(name = "total_amounts")
    private int value;

    public Money(int value) {
        this.value = value;
    }

    public static Money of(int value){
        return new Money(value);
    }
}
