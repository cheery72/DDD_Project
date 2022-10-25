package com.project.ddd.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.application.dto.BoardCreate;
import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.root.BoardService;
import com.project.ddd.board.value.*;
import com.project.ddd.common.Status;
import com.project.ddd.member.value.MemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class BoardDomainTest {

    @Mock
    private Board board;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @BeforeEach
    @DisplayName("새로운 게시글 저장")
    public void createBoard() {
        BoardCreate boardCreate = new BoardCreate("user1","새로운게시글", List.of("#tag"),List.of("image"));
        BoardId id = BoardId.of();
        Board saveBoard = Board.builder()
                .id(id)
                .boarder(Boarder.of(MemberId.of(boardCreate.getMemberId())))
                .content(BoardContent.of(boardCreate.getContent()))
                .tags(BoardTag.of(boardCreate.getTag()))
                .likeMembers(null)
                .images(BoardImage.of(boardCreate.getImage()))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();

        when(boardRepository.save(any()))
                .thenReturn(saveBoard);

        Board newBoard = boardService.createBoard(boardCreate);

        assertEquals(saveBoard.getContent().getContent(),newBoard.getContent().getContent());
    }

}
