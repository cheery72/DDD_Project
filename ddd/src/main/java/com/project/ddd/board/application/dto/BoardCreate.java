package com.project.ddd.board.application.dto;

import com.project.ddd.board.value.BoardContent;
import com.project.ddd.board.value.BoardTag;
import com.project.ddd.member.value.MemberId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardCreate {

    private String memberId;

    private String content;

    private List<String> tag;

    private List<String> image;

    public BoardCreate(String memberId, String content, List<String> tag, List<String> image) {
        this.memberId = memberId;
        this.content = content;
        this.tag = tag;
        this.image = image;
    }
}
