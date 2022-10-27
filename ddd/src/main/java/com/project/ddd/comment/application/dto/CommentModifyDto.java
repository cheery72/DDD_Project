package com.project.ddd.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentModifyDto {

    private String commentId;

    private String content;

    private List<String> tag;

    private String image;
}
