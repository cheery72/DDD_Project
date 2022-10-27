package com.project.ddd.comment.value;

import com.project.ddd.board.value.BoardContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentContent {

    private String content;

    public CommentContent(String content) {
        this.content = content;
    }

    public static CommentContent of(String content){
        return new CommentContent(content);
    }

}
