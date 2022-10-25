package com.project.ddd.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.application.dto.BoardCreate;
import com.project.ddd.board.application.dto.BoardDetailDto;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class BoardDomainTest {

    @Mock
    private Board board;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    public Board boardSave(){
        return Board.builder()
                .id(BoardId.of())
                .boarder(Boarder.of(MemberId.of("user1")))
                .content(BoardContent.of("새로운게시글"))
                .tags(BoardTag.of(List.of("#tag")))
                .likeMembers(Collections.emptyList())
                .images(BoardImage.of(List.of("image")))
                .likes(0)
                .status(Status.REGISTRATION)
                .build();
    }


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("새로운 게시글 저장")
    public void createBoard() {
        BoardCreate boardCreate = new BoardCreate("user1","새로운게시글", List.of("#tag"),List.of("image"));
        Board saveBoard = boardSave();

        when(boardRepository.save(any()))
                .thenReturn(saveBoard);

        boardService.createBoard(boardCreate);
//        assertEquals(saveBoard.getId(),boardId);
    }

    @Test
    @DisplayName("게시글 상세조회")
    public void findBoardDetail(){
        Board saveBoard = boardSave();

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        Optional<Board> optionalBoard = boardRepository.findById(Objects.requireNonNull(saveBoard).getId());
        Board newBoard = optionalBoard.orElseThrow(NoSuchElementException::new);
        BoardDetailDto boardDetailDto = BoardDetailDto.BoardDetailDtoBuilder(newBoard);
        assertEquals(Objects.requireNonNull(saveBoard).getId().getId(),boardDetailDto.getId());
    }

    @Test
    @DisplayName("유저 게시글 조회")
    public void findMemberBoard(){
        Board saveBoard = boardSave();


        when(boardRepository.findByBoarder(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        Optional<Board> optionalBoard = boardRepository.findByBoarder(Objects.requireNonNull(saveBoard).getBoarder());
        Board newBoard = optionalBoard.orElseThrow(NoSuchElementException::new);

        assertEquals(Objects.requireNonNull(saveBoard).getId().getId(),newBoard.getId().getId());

        System.out.println();

    }

    @Test
    @DisplayName("게시글 수정")
    public void modifyBoard(){
        Board saveBoard = boardSave();

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        Optional<Board> optionalBoard = boardRepository.findById(Objects.requireNonNull(saveBoard).getId());
        Board newBoard = optionalBoard.orElseThrow(NoSuchElementException::new);
        List<String> images = Arrays.asList("이미지1","이미지2");
        newBoard.changeBoardImage(images);
        List<String> image = BoardImage.imageBuilder(newBoard.getImages());

        assertThat(image).contains("이미지1","이미지2");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoard(){
        Board saveBoard = boardSave();

//        when(boardRepository.save(any()))
//                .thenReturn(Optional.ofNullable(saveBoard));

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        Optional<Board> optionalBoard = boardRepository.findById(Objects.requireNonNull(saveBoard).getId());
        Board newBoard = optionalBoard.orElseThrow(NoSuchElementException::new);
        boardRepository.delete(saveBoard);
        verify(boardRepository).delete(any());
        when(boardRepository.findById(any()))
                .thenReturn(Optional.of(saveBoard));
        when(boardRepository.findById(any()))
                .thenReturn(Optional.of(newBoard));
        Optional<Board> optionalBoard2 = boardRepository.findById(Objects.requireNonNull(newBoard).getId());
        Board newBoard2 = optionalBoard.orElseThrow(NoSuchElementException::new);
//        assertThat(saveBoard).isTrue();
    }
}
