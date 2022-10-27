package com.project.ddd.comment.application.dto;

import com.project.ddd.comment.root.Comment;
import com.project.ddd.comment.value.CommentId;
import com.project.ddd.comment.value.CommentTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CommentDetailDto {
    
    private String commentId;

    private String memberId;

    private String content;

    private List<String> tags;

    private String image;

    private int likes;

    public static List<CommentDetailDto> commentDetailDtoListBuilder(Page<Comment> comments){
        return comments.stream()
                .map(comment -> CommentDetailDto.builder()
                        .commentId(comment.getId().toString())
                        .memberId(comment.getCommenter().getMemberId().getId())
                        .content(comment.getContent().getContent())
                        .tags(comment.getTag().stream().map(CommentTag::getTag).collect(Collectors.toList()))
                        .image(comment.getImage().getImage())
                        .likes(comment.getLikes())
                        .build())
                .collect(Collectors.toList());
    }

    public static CommentDetailDto commentDetailDtoBuilder(Comment comment){
        return CommentDetailDto.builder()
                .commentId(comment.getId().toString())
                .memberId(comment.getCommenter().getMemberId().getId())
                .content(comment.getContent().getContent())
                .tags(comment.getTag().stream().map(CommentTag::getTag).collect(Collectors.toList()))
                .image(comment.getImage().getImage())
                .likes(comment.getLikes())
                .build();
    }

    @Builder
    public CommentDetailDto(String commentId, String memberId, String content, List<String> tags, String image, int likes) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.content = content;
        this.tags = tags;
        this.image = image;
        this.likes = likes;
    }
}
