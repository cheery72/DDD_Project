package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.value.*;
import com.project.ddd.common.exception.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.project.ddd.board.root.Board.createBoardBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void createBoard(BoardCreateDto boardCreateDto){
        Board boardBuilder = createBoardBuilder(boardCreateDto);
        boardRepository.save(boardBuilder);
    }

    public BoardDetailDto findBoardDetail(String boardId){
        Board board = boardRepository.findById(BoardId.boardIdBuilder(boardId))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글을 찾을 수 없습니다."));

        return BoardDetailDto.BoardDetailDtoBuilder(board);
    }
}
