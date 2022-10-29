package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardLikeDto;
import com.project.ddd.board.application.dto.BoardModifyDto;
import com.project.ddd.board.value.*;
import com.project.ddd.common.BaseTime;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ddd.board.application.dto.BoardLikeDto.*;

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

    private int price;

    @Builder
    public Board(BoardId id, Boarder boarder, BoardContent content, List<BoardTag> tags, List<BoardLikeMember> likeMembers, List<BoardImage> images, int likes, Status status, int price) {
        this.id = id;
        this.boarder = boarder;
        this.content = content;
        this.tags = tags;
        this.likeMembers = likeMembers;
        this.images = images;
        this.likes = likes;
        this.status = status;
        this.price = price;
    }

    public static Board createBoardBuilder(BoardCreateDto boardCreateDto){
        return Board.builder()
                .id(BoardId.createBoardId())
                .boarder(Boarder.of(MemberId.of(boardCreateDto.getMemberId())))
                .content(BoardContent.of(boardCreateDto.getContent()))
                .tags(BoardTag.of(Optional.ofNullable(boardCreateDto.getTag()).orElseGet(Collections::emptyList)))
                .likeMembers(Collections.emptyList())
                .images(BoardImage.of(Optional.ofNullable(boardCreateDto.getImage()).orElseGet(Collections::emptyList)))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }

    public void deleteBoard(){
        this.status = Status.REMOVAL;
    }

    // TODO : 변경전 태그 리스트 가져오기
    public void changeBoard(BoardModifyDto boardModifyDto){
        this.content = BoardContent.of(boardModifyDto.getContent());
        this.tags = BoardTag.of(boardModifyDto.getTag());
        this.images = BoardImage.of(boardModifyDto.getImage());
    }

    // TODO : 변경전 좋아요 수 가져오기
    public void changeBoardLike(Board board, BoardLikeRequest boardLikeRequest){
        int like = board.getLikes();
        List<String> likeMembers = BoardLikeMember.likeBuilder(board.getLikeMembers());
        String likeMember = boardLikeRequest.getMemberId();

        if((likeMembers).contains(likeMember)){
            likeMembers.remove(likeMember);
            like--;
        }else{
            likeMembers.add(likeMember);
            like++;
        }

        this.likeMembers = BoardLikeMember.ofList(likeMembers);
        this.likes = like;
    }

}
