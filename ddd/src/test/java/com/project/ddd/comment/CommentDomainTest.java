package com.project.ddd.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.*;
import com.project.ddd.comment.application.dto.CommentCreateDto;
import com.project.ddd.comment.application.dto.CommentDetailDto;
import com.project.ddd.comment.application.dto.CommentModifyDto;
import com.project.ddd.comment.root.Comment;
import com.project.ddd.comment.root.CommentRepository;
import com.project.ddd.comment.root.CommentService;
import com.project.ddd.comment.value.*;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CommentDomainTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private BoardId boardId = BoardId.createBoardId();

    public Board boardSave(){
        return Board.builder()
                .id(boardId)
                .boarder(Boarder.of(MemberId.of("user1")))
                .content(BoardContent.of("새로운게시글"))
                .tags(BoardTag.of(List.of("#tag")))
                .likeMembers(List.of(BoardLikeMember.of(MemberId.of("user2"))))
                .images(BoardImage.of(List.of("image")))
                .likes(1)
                .status(Status.REGISTRATION)
                .build();
    }

    private final CommentId id = CommentId.of("asdf");

    public Comment commentSave(){
        return Comment.builder()
                .id(id)
                .boardId(boardId)
                .commenter(Commenter.of(MemberId.of("asdfqwer"),"member1","image"))
                .image(CommentImage.of("이미지1"))
                .content(CommentContent.of("새로운 댓글"))
                .likeMembers(List.of(CommentLikeMember.of(MemberId.of("user2"))))
                .likeMembers(Collections.emptyList())
                .tag(CommentTag.of(List.of("#안녕")))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("새로운 댓글 작성")
    public void createComment() {
        Comment comment = commentSave();
        CommentCreateDto commentCreateDto = new CommentCreateDto("asdf","asdf","새로운 댓글",List.of("#안녕"),"image");

        when(commentRepository.save(any()))
                .thenReturn(comment);

        commentService.createComment(commentCreateDto);
//        assertThat(comment).isEqualTo(newComment);
    }

    @Test
    @DisplayName("댓글 조회")
    public void findComment() {
        Comment comment = commentSave();

        when(commentRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment));

        CommentDetailDto commentDetail = commentService.findCommentDetail(id.toString());

        assertThat(commentDetail.getContent()).isEqualTo(Objects.requireNonNull(comment).getContent().getContent());
    }

    @Test
    @DisplayName("게시글 댓글 페이징 조회")
    public void findBoardPageComments(){
        Board board = boardSave();
        Comment comment = commentSave();
        BoardId boardId = board.getId();
        List<Comment> commentList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            commentList.add(comment);
        }

        Pageable pageable = PageRequest.of(1,2);

        Page<Comment> page = new PageImpl<>(commentList,pageable,commentList.size());

        when(commentRepository.findPageAllByBoardId(pageable,boardId))
                .thenReturn(page);

        Page<Comment> comments = commentRepository.findPageAllByBoardId(pageable, boardId);

//        Page<CommentDetailDto> pageComment = commentService.findPageComment(pageable, boardId.getId());

        assertThat(page).isEqualTo(comments);
    }

    @Test
    @DisplayName("댓글 수정")
    public void modifyComment(){
        Comment comment = commentSave();

        when(commentRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment));
        CommentModifyDto commentModifyDto = new CommentModifyDto(Objects.requireNonNull(comment).getId().toString(),"수정 댓글",List.of("#안녕"),"수정 이미지");
        Optional<Comment> optionalComment = commentRepository.findById(Objects.requireNonNull(comment).getId());
        Comment newComment = optionalComment.orElseThrow(NoSuchElementException::new);
        newComment.changeComment(commentModifyDto);

        assertThat(newComment.getContent().getContent()).isEqualTo("수정 댓글");
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteComment(){
        Comment comment = commentSave();

        comment.deleteComment();

        assertThat(comment.getStatus()).isEqualTo(Status.REMOVAL);
    }

    @Test
    @DisplayName("댓글 좋아요 증가")
    public void increaseLikeSuccess(){
        Comment comment = commentSave();
        String likeMemberId = "user1";

        when(commentRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment));

        int like = Objects.requireNonNull(comment).getLikes();
        List<String> likeMembers = CommentLikeMember.likeBuilder(comment.getLikeMembers());

        if(!(likeMembers).contains(likeMemberId)){
            likeMembers.add(likeMemberId);
            like++;
        }

        assertThat(like).isEqualTo(1);
        assertThat(likeMembers).contains("user1");
    }

    @Test
    @DisplayName("댓글 좋아요 증가 실패")
    public void increaseLikeFail(){
        Comment comment = commentSave();
        String likeMemberId = "user2";

        when(commentRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment));

        int like = Objects.requireNonNull(comment).getLikes();
        List<String> likeMembers = CommentLikeMember.likeBuilder(comment.getLikeMembers());

        if(!(likeMembers).contains(likeMemberId)){
            likeMembers.add(likeMemberId);
            like++;
        }

        assertThat(like).isEqualTo(1);
        assertThat(likeMembers).contains("user2");
    }

    @Test
    @DisplayName("댓글 좋아요 감소")
    public void decreaseLike(){
        Comment comment = commentSave();
        String likeMemberId = "user2";

        when(commentRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment));

        int like = Objects.requireNonNull(comment).getLikes();
        List<String> likeMembers = CommentLikeMember.likeBuilder(comment.getLikeMembers());

        if((likeMembers).contains(likeMemberId)){
            likeMembers.remove(likeMemberId);
            like--;
        }

        assertThat(like).isEqualTo(0);
        assertThat(likeMembers).isEqualTo(Collections.emptyList());
    }
}
