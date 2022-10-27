package com.project.ddd.comment.root;

import com.project.ddd.board.value.BoardId;
import com.project.ddd.comment.application.dto.CommentCreateDto;
import com.project.ddd.comment.application.dto.CommentDetailDto;
import com.project.ddd.comment.application.dto.CommentModifyDto;
import com.project.ddd.comment.value.CommentId;
import com.project.ddd.member.root.Member;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.value.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createComment(CommentCreateDto commentCreateDto){
        Member member = memberRepository.findById(MemberId.of(commentCreateDto.getMemberId()))
                .orElseThrow(NoSuchElementException::new);

        Comment comment = Comment.commentBuilder(commentCreateDto, member);
        commentRepository.save(comment);
    }

    public CommentDetailDto findCommentDetail(String commentId){
        Comment comment = commentRepository.findById(CommentId.of(commentId))
                .orElseThrow(NoSuchElementException::new);

        return CommentDetailDto.commentDetailDtoBuilder(comment);
    }

    public Page<CommentDetailDto> findPageComment(Pageable pageable, String boardId){
        Page<Comment> comments = commentRepository.findPageAllByBoardId(pageable, BoardId.of(boardId));

        return new PageImpl<>(CommentDetailDto.commentDetailDtoListBuilder(comments),pageable, comments.getTotalElements());
    }

    @Transactional
    public void modifyComment(CommentModifyDto commentModifyDto){
        Comment comment = commentRepository.findById(CommentId.of(commentModifyDto.getCommentId()))
                .orElseThrow(NoSuchElementException::new);
        comment.changeComment(commentModifyDto);
    }

    @Transactional
    public void deleteComment(String commentId){
        Comment comment = commentRepository.findById(CommentId.of(commentId))
                .orElseThrow(NoSuchElementException::new);
        comment.deleteComment();
    }


}
