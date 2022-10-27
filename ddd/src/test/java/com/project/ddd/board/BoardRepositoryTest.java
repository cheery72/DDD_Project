package com.project.ddd.board;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.value.BoardContent;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.board.value.BoardTag;
import com.project.ddd.board.value.Boarder;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    @DisplayName("게시글 저장 테스트")
    public void saveBoard() {
        Board board = new Board(BoardId.of(), new Boarder(MemberId.of("user1")),
                new BoardContent("새로운 게시글"), List.of(new BoardTag("#안녕")),
                null,null,0, Status.REGISTRATION);

        Board newBoard = boardRepository.save(board);

        assertThat(newBoard.getId().getId()).isEqualTo(board.getId().getId());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void selectBoard() {
        Optional<Board> optionalBoard = boardRepository.findById(BoardId.of());
        Board board = optionalBoard.orElseThrow(NoSuchElementException::new);
        BoardId boardId = board.getId();
        assertThat(boardId.getId()).isEqualTo("first");
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updateBoard() {
        Optional<Board> optionalBoard = boardRepository.findById(BoardId.of());
        Board board = optionalBoard.orElseThrow(NoSuchElementException::new);

//        board.setContent(new BoardContent("수정된 게시글"));
        BoardContent boardContent = board.getContent();
        assertThat(boardContent.getContent()).isEqualTo("수정된 게시글");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoard() {
        boardRepository.deleteById(BoardId.of());

        Optional<Board> optionalBoard = boardRepository.findById(BoardId.of());

        assertThat(optionalBoard).isEmpty();

    }
}
