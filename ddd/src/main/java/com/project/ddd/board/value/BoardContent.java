package com.project.ddd.board.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardContent {

    private String content;

    public BoardContent(String content) {
        this.content = content;
    }
}
