package com.project.ddd.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentModifyDto {

    @NotBlank
    private String commentId;

    private String content;

    private List<String> tag;

    private String image;
}
