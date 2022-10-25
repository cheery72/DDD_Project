package com.project.ddd.board.root;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.board.value.Boarder;
import com.project.ddd.member.value.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, BoardId> {
    Optional<Board> findByBoarder(Boarder boarder);
}
