package com.project.ddd.board.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTag {

    private String tag;

    public BoardTag(String tag) {
        this.tag = tag;
    }
}
