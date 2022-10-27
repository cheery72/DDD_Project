package com.project.ddd.board.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BoardLikeDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardLikeRequest {

        @NotBlank
        private String boardId;

        @NotBlank
        private String memberId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class BoardLikeResponse {

        private int likes;

        private List<String> memberList;
    }
}
