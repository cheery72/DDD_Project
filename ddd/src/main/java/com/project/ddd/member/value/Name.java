package com.project.ddd.member.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Name {

    @Column(name = "name")
    private String value;

    public Name(String value) {
        this.value = value;
    }

    public static Name of(String name) {
        return new Name(name);
    }

}
