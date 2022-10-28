package com.project.ddd.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.value.BoardId;
import com.project.ddd.comment.application.dto.CommentCreateDto;
import com.project.ddd.comment.application.dto.CommentModifyDto;
import com.project.ddd.comment.presentation.CommentController;
import com.project.ddd.comment.root.Comment;
import com.project.ddd.comment.root.CommentRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CommentControllerTest  {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentRepository commentRepository;

    private final String BASE_URL = "/comment";

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
    public void createComment() throws Exception {

        String boardId = "346558b07dc34359b737dbfc3226078c";
        String memberId = "asdf";
        String content = "새로운 댓글";
        List<String> tags = List.of("#안녕");
        String image = "이미지";

        String body = objectMapper.writeValueAsString(
                CommentCreateDto.builder()
                        .boardId(boardId)
                        .memberId(memberId)
                        .content(content)
                        .image(image)
                        .tags(tags)
                        .build()
        );

        mockMvc.perform(post(BASE_URL+"/create")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("댓글 상세정보 조회")
    public void findCommentDetail() throws Exception {
        String commentId = "5bcdbc22ea3a4c1092dad74384b826e2";

        mockMvc.perform(get(BASE_URL+"/"+commentId+"/detail"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 페이징 조회")
    public void findPageComment() throws Exception {
        String boardId = "346558b07dc34359b737dbfc3226078c";

        mockMvc.perform(get(BASE_URL+"/"+boardId+"/page")
                .param("page","0")
                .param("size","1")
                .param("sort","createDate,desc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(commentRepository).findPageAllByBoardId(pageableCaptor.capture(), BoardId.of(boardId));
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertThat(pageable.getPageNumber()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 수정")
    public void modifyComment() throws Exception {
        String body = objectMapper
                .writeValueAsString(new CommentModifyDto("5bcdbc22ea3a4c1092dad74384b826e2","asdf",List.of("asdf"),"asdf"));

        mockMvc.perform(put(BASE_URL+"/modify")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteComment() throws Exception {
        String commentId = "5b818662119e473399f229a15f8ff4fa";

        mockMvc.perform(delete(BASE_URL+"/"+commentId))
                .andExpect(status().isNoContent());
    }
}
