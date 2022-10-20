package com.project.ddd.board.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage {

    private String image;

    public BoardImage(String image) {
        this.image = image;
    }
}
