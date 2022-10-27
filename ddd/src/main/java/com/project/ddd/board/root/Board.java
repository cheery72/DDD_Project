package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.value.*;
import com.project.ddd.common.BaseTime;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTime {

    @EmbeddedId
    private BoardId id;

    @Embedded
    private Boarder boarder;

    @Embedded
    private BoardContent content;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "board_tag", joinColumns = @JoinColumn(name = "board_id"))
    @OrderColumn(name = "tag_idx")
    private List<BoardTag> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "like_member_idx")
    @CollectionTable(name = "board_like_member", joinColumns = @JoinColumn(name = "board_id"))
    private List<BoardLikeMember> likeMembers;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "board_image", joinColumns = @JoinColumn(name = "board_id"))
    @OrderColumn(name = "image_idx")
    private List<BoardImage> images;

    private int likes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Board(BoardId id, Boarder boarder, BoardContent content, List<BoardTag> tags, List<BoardLikeMember> likeMembers, List<BoardImage> images, int likes, Status status) {
        this.id = id;
        this.boarder = boarder;
        this.content = content;
        this.tags = tags;
        this.likeMembers = likeMembers;
        this.images = images;
        this.likes = likes;
        this.status = status;
    }

    public static Board createBoardBuilder(BoardCreateDto boardCreateDto){
        return Board.builder()
                .id(BoardId.of())
                .boarder(Boarder.of(MemberId.of(boardCreateDto.getMemberId())))
                .content(BoardContent.of(boardCreateDto.getContent()))
                .tags(BoardTag.of(Optional.ofNullable(boardCreateDto.getTag()).orElseGet(Collections::emptyList)))
                .likeMembers(Collections.emptyList())
                .images(BoardImage.of(Optional.ofNullable(boardCreateDto.getImage()).orElseGet(Collections::emptyList)))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }


    public void changeBoardContent(BoardContent boardContent){
        this.content = boardContent;
    }

    // TODO : 변경전 태그 리스트 가져오기
    public void changeBoardTags(List<BoardTag> tags){
        this.tags = tags;
    }

    // TODO : 변경전 좋아요 멤버 리스트 가져오기
    public void changeBoardLikeMember(List<BoardLikeMember> likeMembers){
        this.likeMembers = likeMembers;
    }

    // TODO : 변경전 이미지 가져오기
    public void changeBoardImage(List<String> images){
        this.images = BoardImage.of(images);
    }

    // TODO : 변경전 좋아요 수 가져오기
    public void changeBoardLike(){
//        this.likes =
    }

}
