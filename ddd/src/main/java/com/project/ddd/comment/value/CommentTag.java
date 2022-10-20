package com.project.ddd.comment.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentTag {

    private String tag;

    public CommentTag(String tag) {
        this.tag = tag;
    }
}
