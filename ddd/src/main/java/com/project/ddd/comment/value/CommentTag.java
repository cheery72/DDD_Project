package com.project.ddd.comment.value;

import com.project.ddd.board.value.BoardTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentTag {

    private String tag;

    public CommentTag(String tag) {
        this.tag = tag;
    }

    public static List<CommentTag> of(List<String> tag) {
        return tag.stream().map(CommentTag::new).collect(Collectors.toList());
    }
}
