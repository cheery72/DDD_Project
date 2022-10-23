package com.project.ddd.board.root;

import com.project.ddd.board.value.*;
import com.project.ddd.common.BaseTime;
import com.project.ddd.common.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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
}
