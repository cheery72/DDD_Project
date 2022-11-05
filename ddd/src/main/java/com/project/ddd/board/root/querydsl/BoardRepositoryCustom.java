package com.project.ddd.board.root.querydsl;

import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.board.value.Boarder;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> findPageAllByBoarder(Pageable pageable, Boarder boarder);
}
