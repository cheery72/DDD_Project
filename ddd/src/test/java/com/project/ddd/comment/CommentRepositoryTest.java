package com.project.ddd.comment;

import com.project.ddd.comment.root.Comment;
import com.project.ddd.comment.root.CommentRepository;
import com.project.ddd.comment.value.*;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private final CommentId id = CommentId.of("asdf");

    public Comment commentSave(){
        return Comment.builder()
                .id(id)
                .commenter(Commenter.of(MemberId.of("asdfqwer"),"member1","image"))
                .image(CommentImage.of("이미지1"))
                .content(CommentContent.of("새로운 댓글"))
                .tag(CommentTag.of(List.of("#안녕")))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }

    @BeforeEach
    @DisplayName("댓글 저장 테스트")
    public void saveComment() {
        Comment comment = commentSave();

        Comment newComment = commentRepository.save(comment);

        assertThat(newComment.getId()).isEqualTo(comment.getId());
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    public void selectComment() {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment newComment = optionalComment.orElseThrow(NoSuchElementException::new);

        assertThat(id).isEqualTo(newComment.getId());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void updateComment() {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment = optionalComment.orElseThrow(NoSuchElementException::new);

//        comment.setContent(new CommentContent("수정된 댓글"));
        CommentContent commentContent = comment.getContent();
        assertThat(commentContent.getContent()).isEqualTo("수정된 댓글");
    }
//
    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteComment() {
        commentRepository.deleteById(id);

        Optional<Comment> optionalComment = commentRepository.findById(id);

        assertThat(optionalComment).isEmpty();

    }
}
