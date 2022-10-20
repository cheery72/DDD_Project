package com.project.ddd.comment.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentImage {

    private String image;

    public CommentImage(String image) {
        this.image = image;
    }
}
