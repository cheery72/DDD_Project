package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreate;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.value.*;
import com.project.ddd.common.Status;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.value.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.project.ddd.board.root.Board.createBoardBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(BoardCreate boardCreate){
        Board boardBuilder = createBoardBuilder(boardCreate);
        boardRepository.save(boardBuilder);
    }

    public BoardDetailDto findBoardDetail(String boardId){
        Optional<Board> optionalBoard = boardRepository.findById(BoardId.boardIdBuilder(boardId));
        Board board = optionalBoard.orElseThrow(NoSuchElementException::new);

        return BoardDetailDto.BoardDetailDtoBuilder(board);
    }
}
