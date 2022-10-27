package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.value.*;
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
        Optional<Board> optionalBoard = boardRepository.findById(BoardId.boardIdBuilder(boardId));
        Board board = optionalBoard.orElseThrow(NoSuchElementException::new);

        return BoardDetailDto.BoardDetailDtoBuilder(board);
    }
}
