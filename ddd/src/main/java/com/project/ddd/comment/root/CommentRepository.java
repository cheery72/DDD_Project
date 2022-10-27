package com.project.ddd.comment.root;

import com.project.ddd.board.value.BoardId;
import com.project.ddd.comment.root.Comment;
import com.project.ddd.comment.value.CommentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
    Page<Comment> findPageAllByBoardId(Pageable pageable, BoardId boardId);
}
