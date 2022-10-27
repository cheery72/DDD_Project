package com.project.ddd.comment.value;

import com.project.ddd.board.value.BoardImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentImage {

    private String image;

    public CommentImage(String image) {
        this.image = image;
    }


    public static CommentImage of(String image){
        return new CommentImage(image);
    }
}
