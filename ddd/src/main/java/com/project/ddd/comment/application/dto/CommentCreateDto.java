package com.project.ddd.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentCreateDto {

    @NotBlank
    private String boardId;

    @NotBlank
    private String memberId;

    private String content;

    private List<String> tags;

    private String image;

}
