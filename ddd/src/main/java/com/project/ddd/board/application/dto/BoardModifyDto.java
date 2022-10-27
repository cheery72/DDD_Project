package com.project.ddd.board.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardModifyDto {

    @NotBlank
    private String boardId;

    private String content;

    private List<String> tag;

    private List<String> image;

}
