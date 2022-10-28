package com.project.ddd.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.application.dto.BoardCreateDto;
import com.project.ddd.board.application.dto.BoardDetailDto;
import com.project.ddd.board.application.dto.BoardModifyDto;
import com.project.ddd.board.root.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;
import java.util.List;

import static com.project.ddd.board.application.dto.BoardLikeDto.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardRepository boardRepository;

    private final String BASE_URL = "/board";

    private final String boardId = "caf3a51d9a47401a99872a91c743bc9c";

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach()
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    public void createBoard() throws Exception {

        String memberId = "asdf";
        String content = "새로운 게시글";
        List<String> tags = List.of("#안녕");
        List<String> images = List.of("이미지","이미지2");

        String body = objectMapper.writeValueAsString(
                BoardCreateDto.builder()
                        .memberId(memberId)
                        .content(content)
                        .image(images)
                        .tag(tags)
                        .build()
        );

        mockMvc.perform(post(BASE_URL+"/create")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("게시글 상세정보 조회")
    public void findBoardDetail() throws Exception {
        BoardDetailDto boardDetailDto =
                new BoardDetailDto("caf3a51d9a47401a99872a91c743bc9c","asdf","첫번째 게시글"
                        ,Collections.emptyList(),Collections.emptyList(),List.of("이미지1","이미지2"),0);

        mockMvc.perform(get(BASE_URL+"/"+boardId+"/detail"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(boardDetailDto)));
    }

    @Test
    @DisplayName("게시글 페이징 조회")
    public void findPageBoard() throws Exception {
        String memberId = "user1";

        mockMvc.perform(get(BASE_URL+"/"+memberId+"/member-board")
                .param("page","0")
                .param("size","1")
                .param("sort","createDate,desc"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 수정")
    public void modifyBoard() throws Exception {
        String body = objectMapper
                .writeValueAsString(new BoardModifyDto(boardId,"asdf",List.of("asdf"),List.of("asdf")));

        mockMvc.perform(put(BASE_URL+"/modify")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoard() throws Exception {
        mockMvc.perform(delete(BASE_URL+"/"+boardId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("게시글 좋아요 추가")
    public void increaseLikeBoard() throws Exception {
        String body = objectMapper
                .writeValueAsString(new BoardLikeRequest(boardId,"user2"));
        String response = objectMapper
                .writeValueAsString(new BoardLikeResponse(1,List.of("user2")));

        mockMvc.perform(put(BASE_URL+"/like")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));

    }

    @Test
    @DisplayName("게시글 좋아요 감소")
    public void decreaseLikeBoard() throws Exception {
        String body = objectMapper
                .writeValueAsString(new BoardLikeRequest(boardId,"user2"));
        String response = objectMapper
                .writeValueAsString(new BoardLikeResponse(0, Collections.emptyList()));

        mockMvc.perform(put(BASE_URL+"/like")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(response)));

    }
}
