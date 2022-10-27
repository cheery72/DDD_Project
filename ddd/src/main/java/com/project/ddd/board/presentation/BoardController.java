package com.project.ddd.board.presentation;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.root.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Object> boardCreate(@RequestBody @Valid BoardCreateDto boardCreateDto){
        log.info("createBoard start ----");

        boardService.createBoard(boardCreateDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}/detail")
    public ResponseEntity<BoardDetailDto> boardDetailFind(@PathVariable String boardId){
        log.info("detail board start ----");

        return ResponseEntity
                .ok(boardService.findBoardDetail(boardId));
    }
}
