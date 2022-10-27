package com.project.ddd.board.presentation;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.root.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<Object> boardCreate(@RequestBody @Valid BoardCreateDto boardCreateDto){
        log.debug("createBoard start ----");

        boardService.createBoard(boardCreateDto);

        return ResponseEntity.noContent().build();
    }
}
