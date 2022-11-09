package com.project.ddd.shorts.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsContent {

    private String content;

    public ShortsContent(String content) {
        this.content = content;
    }

    public static ShortsContent of(String content){
        return new ShortsContent(content);
    }
}
