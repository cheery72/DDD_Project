package com.project.ddd.board.root;

import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.application.dto.BoardListMemberDto;
import com.project.ddd.board.application.dto.BoardModifyDto;
import com.project.ddd.board.value.*;
import com.project.ddd.common.exception.NoSuchBoardException;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.value.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public void createBoard(BoardCreateDto boardCreateDto){
        Board boardBuilder = createBoardBuilder(boardCreateDto);
        boardRepository.save(boardBuilder);
    }

    public BoardDetailDto findBoardDetail(String boardId){
        Board board = boardRepository.findById(BoardId.of(boardId))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글을 찾을 수 없습니다."));

        return BoardDetailDto.BoardDetailDtoBuilder(board);
    }

    public Page<BoardListMemberDto> findBoardPageListMember(Pageable pageable, String memberId){
        Page<Board> boardList = boardRepository.findPageAllByBoarder(pageable,Boarder.of(MemberId.of(memberId)));

        return new PageImpl<>(BoardListMemberDto.boardListMemberDtoBuilder(boardList),pageable,boardList.getTotalElements());
    }

    @Transactional
    public void modifyBoard(BoardModifyDto boardModifyDto){
        Board board = boardRepository.findById(BoardId.of(boardModifyDto.getBoardId()))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글을 찾을 수 없습니다."));
        board.changeBoard(boardModifyDto);
    }

    @Transactional
    public void deleteBoard(String boardId){
        Board board = boardRepository.findById(BoardId.of(boardId))
                .orElseThrow(() -> new NoSuchBoardException("요청한 게시글을 찾을 수 없습니다."));
        boardRepository.deleteById(board.getId());
    }
}
