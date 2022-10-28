package com.project.ddd.board.presentation;

import com.project.ddd.board.application.dto.*;
import com.project.ddd.board.root.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.project.ddd.board.application.dto.BoardLikeDto.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Object> boardCreate(@RequestBody @Valid BoardCreateDto boardCreateDto){
        log.info("board create start ----");

        boardService.createBoard(boardCreateDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}/detail")
    public ResponseEntity<BoardDetailDto> boardDetailFind(@PathVariable String boardId){
        log.info("detail board start ----");

        return ResponseEntity
                .ok(boardService.findBoardDetail(boardId));
    }

    @GetMapping("/{memberId}/member-board")
    public ResponseEntity<Page<BoardListMemberDto>> boardPageListMemberFind(@PageableDefault(size = 4, sort = "createDate",
                                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                                                           @PathVariable String memberId){
        log.info("member board list find start ---- ");

        return ResponseEntity
                .ok(boardService.findBoardPageListMember(pageable,memberId));
    }

    @PutMapping("/modify")
    public ResponseEntity<Object> boardModify(@RequestBody @Valid BoardModifyDto boardModifyDto){
        log.info("board modify start ----");

        boardService.modifyBoard(boardModifyDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Object> boardDelete(@PathVariable String boardId){
        log.info("board delete start ----");

        boardService.deleteBoard(boardId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/like")
    public ResponseEntity<BoardLikeResponse> boardLikeChange(@RequestBody @Valid BoardLikeRequest boardLikeRequest){
        log.info("board like start ----");

        return ResponseEntity
                .ok()
                .body(boardService.changeLikeBoard(boardLikeRequest));
    }
}
