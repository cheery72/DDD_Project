package com.project.ddd.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.board.root.BoardService;
import com.project.ddd.board.value.*;
import com.project.ddd.common.Status;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.value.MemberId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
                .id(BoardId.createBoardId())
                .boarder(Boarder.of(MemberId.of("user1")))
                .content(BoardContent.of("새로운게시글"))
                .tags(BoardTag.of(List.of("#tag")))
                .likeMembers(List.of(BoardLikeMember.of(MemberId.of("user2"))))
                .images(BoardImage.of(List.of("image")))
                .likes(1)
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
        BoardCreateDto boardCreateDto = new BoardCreateDto("user1","새로운게시글", List.of("#tag"),List.of("image"),50000);
        Board saveBoard = boardSave();

        when(boardRepository.save(any()))
                .thenReturn(saveBoard);

        boardService.createBoard(boardCreateDto);
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
    @DisplayName("유저 게시글 페이징 조회")
    public void findMemberBoard(){
        Board saveBoard = boardSave();
        List<Board> boardList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            boardList.add(board);
        }

        Pageable pageable = PageRequest.of(1,2);

        String id = saveBoard.getBoarder().getMemberId().getId();

        MemberId memberId = MemberId.of(id);
        Boarder boarder = Boarder.of(memberId);

        when(boardRepository.findPageAllByBoarder(pageable,boarder))
                .thenReturn(boardList);

        List<Board> newBoardList = boardRepository.findPageAllByBoarder(pageable,boarder);

        Page<Board> page = new PageImpl<>(boardList,pageable,boardList.size());
        Page<Board> newPage = new PageImpl<>(newBoardList,pageable,boardList.size());

        assertThat(page).isEqualTo(newPage);

    }

    @Test
    @DisplayName("페이징 게시글 조회")
    public void findPaginationBoard(){
        Board board = boardSave();
        List<Board> boardList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            boardList.add(board);
        }

        Pageable pageable = PageRequest.of(1,2);
        Page<Board> page = new PageImpl<>(boardList,pageable,boardList.size());

        when(boardRepository.findAll(pageable))
                .thenReturn(page);

        Page<Board> boardPage = boardRepository.findAll(pageable);

        assertThat(page).isEqualTo(boardPage);

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
//        newBoard.changeBoardImage(images);
        List<String> image = BoardImage.imageBuilder(newBoard.getImages());

        assertThat(image).contains("이미지1","이미지2");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoard(){
        Board saveBoard = boardSave();

        saveBoard.deleteBoard();

        assertThat(saveBoard.getStatus()).isEqualTo(Status.REMOVAL);

    }

    @Test
    @DisplayName("게시글 좋아요 추가")
    public void increaseLikeBoard(){
        Board saveBoard = boardSave();
        String likeMemberId = "user1";

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        int like = Objects.requireNonNull(saveBoard).getLikes();
        List<String> likeMembers = BoardLikeMember.likeBuilder(saveBoard.getLikeMembers());

        if(!(likeMembers).contains(likeMemberId)){
            likeMembers.add(likeMemberId);
            like++;
        }

        assertThat(like).isEqualTo(2);
        assertThat(likeMembers).contains("user1");
    }

    @Test
    @DisplayName("게시글 좋아요 추가 실패")
    public void increaseLikeBoardFail(){
        Board saveBoard = boardSave();
        String likeMemberId = "user2";

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        int like = Objects.requireNonNull(saveBoard).getLikes();
        List<String> likeMembers = BoardLikeMember.likeBuilder(saveBoard.getLikeMembers());

        if(!(likeMembers).contains(likeMemberId)){
            likeMembers.add(likeMemberId);
            like++;
        }

        assertThat(like).isEqualTo(1);
        assertThat(likeMembers).contains("user2");
    }

    @Test
    @DisplayName("게시글 좋아요 해제")
    public void decreaseLikeBoard(){
        Board saveBoard = boardSave();
        String likeMemberId = "user2";

        when(boardRepository.findById(any()))
                .thenReturn(Optional.ofNullable(saveBoard));

        int like = Objects.requireNonNull(saveBoard).getLikes();
        List<String> likeMembers = BoardLikeMember.likeBuilder(saveBoard.getLikeMembers());

        if((likeMembers).contains(likeMemberId)){
            likeMembers.remove(likeMemberId);
            like--;
        }

        assertThat(like).isEqualTo(0);
        assertThat(likeMembers).isEqualTo(Collections.emptyList());
    }
}
