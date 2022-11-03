package com.project.ddd.board.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class BoardCreateDto {

    @NotBlank
    private String memberId;

    private String content;

    private List<String> tag;

    private List<String> image;

    private int price;

    public BoardCreateDto(String memberId, String content, List<String> tag, List<String> image, int price) {
        this.memberId = memberId;
        this.content = content;
        this.tag = tag;
        this.image = image;
        this.price = price;
    }
}
