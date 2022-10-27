package com.project.ddd.board.application.dto;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardImage;
import com.project.ddd.board.value.BoardLikeMember;
import com.project.ddd.board.value.BoardTag;
import com.project.ddd.member.value.MemberId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardListMemberDto {

    private String boardId;

    private String content;

    private List<String> tags;

    private List<String> likeMembers;

    private List<String> images;

    private int likes;

    public static List<BoardListMemberDto> boardListMemberDtoBuilder(Page<Board> boards){
        return boards.stream()
                .map(board -> BoardListMemberDto.builder()
                        .boardId(board.getId().getId())
                        .content(board.getContent().getContent())
                        .tags(board.getTags().stream().map(BoardTag::getTag).collect(Collectors.toList()))
                        .likeMembers(board.getLikeMembers().stream().map(BoardLikeMember::getMemberId).map(MemberId::getId).collect(Collectors.toList()))
                        .images(BoardImage.imageBuilder(board.getImages()))
                        .likes(board.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    public BoardListMemberDto(String boardId, String content, List<String> tags, List<String> likeMembers, List<String> images, int likes) {
        this.boardId = boardId;
        this.content = content;
        this.tags = tags;
        this.likeMembers = likeMembers;
        this.images = images;
        this.likes = likes;
    }
}
