package com.project.ddd.board.application.dto;

import com.project.ddd.board.value.BoardContent;
import com.project.ddd.board.value.BoardTag;
import com.project.ddd.member.value.MemberId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardCreate {

    private MemberId memberId;

    private BoardContent content;

    private List<BoardTag> tag;

    public BoardCreate(MemberId memberId, BoardContent content, List<BoardTag> tag) {
        this.memberId = memberId;
        this.content = content;
        this.tag = tag;
    }
}
