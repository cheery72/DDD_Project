package com.project.ddd.member.value;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private String name;

    private int discount;

    public static Coupon of(String name, int discount){
        return new Coupon(name,discount);
    }
}
