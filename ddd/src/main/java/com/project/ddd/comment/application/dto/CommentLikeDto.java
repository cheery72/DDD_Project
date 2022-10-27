package com.project.ddd.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CommentLikeDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentLikeRequest {

        @NotBlank
        private String commentId;

        @NotBlank
        private String memberId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CommentLikeResponse {

        private int likes;

        private List<String> memberList;
    }
}
