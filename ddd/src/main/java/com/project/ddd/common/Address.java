package com.project.ddd.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;

    private String gu;

    private String dong;

    private String detail;

    public Address(String city, String gu, String dong, String detail) {
        this.city = city;
        this.gu = gu;
        this.dong = dong;
        this.detail = detail;
    }

    public static Address of(String city, String gu, String dong, String detail){
        return new Address(city,gu,dong,detail);
    }
}
