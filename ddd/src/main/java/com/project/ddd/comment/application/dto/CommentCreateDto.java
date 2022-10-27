package com.project.ddd.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentCreateDto {

    private String boardId;

    private String memberId;

    private String content;

    private List<String> tags;

    private String image;

}
