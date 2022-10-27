package com.project.ddd.member.value;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberImage {

    private String image;

    public MemberImage(String image) {
        this.image = image;
    }

    public static MemberImage of(String image) {
        return new MemberImage(image);
    }

}
