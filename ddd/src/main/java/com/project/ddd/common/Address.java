package com.project.ddd.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String metropolitanCity;

    private String city;

    private String gu;

    private String dong;

    private String detail;

    public Address(String metropolitanCity, String city, String gu, String dong, String detail) {
        this.metropolitanCity = metropolitanCity;
        this.city = city;
        this.gu = gu;
        this.dong = dong;
        this.detail = detail;
    }

    public static Address of(String metropolitanCity, String city, String gu, String dong, String detail){
        return new Address(metropolitanCity,city,gu,dong,detail);
    }
}
