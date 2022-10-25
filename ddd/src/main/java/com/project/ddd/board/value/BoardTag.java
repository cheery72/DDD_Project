package com.project.ddd.board.value;

import com.project.ddd.board.root.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTag {

    private String tag;

    public BoardTag(String tag) {
        this.tag = tag;
    }

    public static List<BoardTag> of(List<String> tag) {
        return tag.stream().map(BoardTag::new).collect(Collectors.toList());
    }
}
