package com.project.ddd.board.root;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.value.BoardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, BoardId> {
}
