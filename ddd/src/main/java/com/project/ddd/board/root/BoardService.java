package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreate;
import com.project.ddd.member.root.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.ddd.board.root.Board.createBoardBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Board createBoard(BoardCreate boardCreate){
        return boardRepository.save(createBoardBuilder(boardCreate));
    }
}
