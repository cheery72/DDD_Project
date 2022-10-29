package com.project.ddd.order.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receiver {

    @Column(name = "receiver_name")
    private String name;

    @Column(name = "receiver_phone")
    private String phone;

    public Receiver(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public static Receiver of(String name, String phone){
        return new Receiver(name,phone);
    }
}
