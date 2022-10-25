package com.project.ddd.board.application.dto;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardImage;
import com.project.ddd.board.value.BoardTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardDetailDto {

    private String id;

    private String memberId;

    private String content;

    private List<String> tags;

    private List<String> likeMembers;

    private List<String> images;

    private int likes;

    public static BoardDetailDto BoardDetailDtoBuilder(Board board){
        return BoardDetailDto.builder()
                .id(board.getId().getId())
                .memberId(board.getBoarder().getMemberId().getId())
                .content(board.getContent().getContent())
                .tags(board.getTags().stream().map(BoardTag::getTag).collect(Collectors.toList()))
                .likeMembers(Optional.ofNullable(board.getLikeMembers())
                        .orElseGet(Collections::emptyList)
                        .stream().map(e -> e.getMemberId().getId()).collect(Collectors.toList()))
                .images(board.getImages().stream().map(BoardImage::getImage).collect(Collectors.toList()))
                .likes(board.getLikes())
                .build();
    }

    @Builder
    public BoardDetailDto(String id, String memberId, String content, List<String> tags, List<String> likeMembers, List<String> images, int likes) {
        this.id = id;
        this.memberId = memberId;
        this.content = content;
        this.tags = tags;
        this.likeMembers = likeMembers;
        this.images = images;
        this.likes = likes;
    }
}
