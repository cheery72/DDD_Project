package com.project.ddd.comment.presentation;

import com.project.ddd.comment.application.dto.CommentCreateDto;
import com.project.ddd.comment.application.dto.CommentDetailDto;
import com.project.ddd.comment.application.dto.CommentModifyDto;
import com.project.ddd.comment.root.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.project.ddd.comment.application.dto.CommentLikeDto.*;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Object> commentCreate(@RequestBody @Valid CommentCreateDto commentCreateDto){
        log.info("comment create start ----");

        commentService.createComment(commentCreateDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{commentId}/detail")
    public ResponseEntity<CommentDetailDto> commentDetailFind(@PathVariable String commentId){
        log.info("detail comment start ----");

        return ResponseEntity
                .ok(commentService.findCommentDetail(commentId));
    }

    @GetMapping("/{boardId}/page")
    public ResponseEntity<Page<CommentDetailDto>> commentPageFind(@PageableDefault(size = 4, sort = "createDate",
                                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                                                           @PathVariable String boardId){
        log.info("comment pagination list find start ---- ");

        return ResponseEntity
                .ok(commentService.findPageComment(pageable,boardId));
    }

    @PutMapping("/modify")
    public ResponseEntity<Object> commentModify(@RequestBody @Valid CommentModifyDto commentModifyDto){
        log.info("comment modify start ----");

        commentService.modifyComment(commentModifyDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> commentDelete(@PathVariable String commentId){
        log.info("comment delete start ----");

        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/like")
    public ResponseEntity<CommentLikeResponse> commentLikeChange(@RequestBody @Valid CommentLikeRequest commentLikeRequest){
        log.info("comment like start ----");

        return ResponseEntity
                .ok()
                .body(commentService.changeLikeBoard(commentLikeRequest));
    }
}
