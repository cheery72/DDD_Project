package com.project.ddd.shorts.value;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsImage {

    private String image;

    public ShortsImage(String image) {
        this.image = image;
    }

    public static ShortsImage of(String image){
        return new ShortsImage(image);
    }

}
