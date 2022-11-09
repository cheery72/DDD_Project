package com.project.ddd.comment.root;

import com.project.ddd.board.value.BoardId;
import com.project.ddd.comment.application.dto.CommentCreateDto;
import com.project.ddd.comment.application.dto.CommentModifyDto;
import com.project.ddd.comment.value.*;
import com.project.ddd.common.*;
import com.project.ddd.member.root.Member;
import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ddd.comment.application.dto.CommentLikeDto.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTime {

    @EmbeddedId
    private CommentId id;

    @Embedded
    private BoardId boardId;

    @Embedded
    private Commenter commenter;

    @Embedded
    private CommentImage image;

    @Embedded
    private CommentContent content;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "comment_tag", joinColumns = @JoinColumn(name = "comment_id"))
    @OrderColumn(name = "tag_idx")
    private List<CommentTag> tag = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "like_member_idx")
    @CollectionTable(name = "comment_like_member", joinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentLikeMember> likeMembers = new ArrayList<>();

    private int likes;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status")
    private Status status;

    public void changeComment(CommentModifyDto commentModifyDto){
        this.content = CommentContent.of(commentModifyDto.getContent());
        this.image = CommentImage.of(commentModifyDto.getImage());
        this.tag = CommentTag.of(commentModifyDto.getTag());
    }

    public void deleteComment(){
        this.status = Status.REMOVAL;
    }

    public void changeCommentLike(Comment comment, CommentLikeRequest commentLikeRequest){
        int like = comment.getLikes();
        List<String> likeMembers = CommentLikeMember.likeBuilder(comment.getLikeMembers());
        String likeMember = commentLikeRequest.getMemberId();

        if((likeMembers).contains(likeMember)){
            likeMembers.remove(likeMember);
            like--;
        }else{
            likeMembers.add(likeMember);
            like++;
        }

        this.likeMembers = CommentLikeMember.ofList(likeMembers);
        this.likes = like;

    }

    public static Comment commentBuilder(CommentCreateDto commentCreateDto, Member member) {
        return Comment.builder()
                .id(CommentId.createCommentId())
                .boardId(BoardId.of(commentCreateDto.getBoardId()))
                .commenter(Commenter.of(MemberId.of(commentCreateDto.getMemberId()), member.getName().toString(),member.getImage().toString()))
                .image(CommentImage.of(commentCreateDto.getImage()))
                .content(CommentContent.of(commentCreateDto.getContent()))
                .tag(Optional.ofNullable(CommentTag.of(commentCreateDto.getTags())).orElseGet(Collections::emptyList))
                .likeMembers(Collections.emptyList())
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }

    @Builder
    public Comment(CommentId id, BoardId boardId, Commenter commenter, CommentImage image, CommentContent content, List<CommentTag> tag, List<CommentLikeMember> likeMembers, int likes, Status status) {
        this.id = id;
        this.boardId = boardId;
        this.commenter = commenter;
        this.image = image;
        this.content = content;
        this.tag = tag;
        this.likeMembers = likeMembers;
        this.likes = likes;
        this.status = status;
    }
}
