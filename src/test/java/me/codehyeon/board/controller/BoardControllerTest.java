package me.codehyeon.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.codehyeon.board.dto.BoardRequestDto;
import me.codehyeon.board.entity.Board;
import me.codehyeon.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  // 테스트 후 롤백
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    private Board savedBoard;

    @BeforeEach
    void setup() {
        Board board = new Board(new BoardRequestDto("테스트 제목", "테스트 내용"));
        savedBoard = boardRepository.save(board);
        // System.out.println(savedBoard.getId()); // id 제대로 생성되는지
    }


    @Test
    void 게시글_작성_API_테스트() throws Exception {
        // Given
        BoardRequestDto requestDto = new BoardRequestDto("새 게시글", "새 내용");

        // When & Then
        mockMvc.perform(post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("새 게시글"))
                .andExpect(jsonPath("$.content").value("새 내용"));
    }

    @Test
    void 특정_게시글_조회_API_테스트() throws Exception {
        // When & Then
        mockMvc.perform(get("/boards/" + savedBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"));
    }

    @Test
    void 모든_게시글_조회_API_테스트() throws Exception {
        // When & Then
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글_수정_API_테스트() throws Exception {
        // Given
        BoardRequestDto updateDto = new BoardRequestDto("수정된 제목", "수정된 내용");

        // When & Then
        mockMvc.perform(put("/boards/" + savedBoard.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.content").value("수정된 내용"));
    }

    @Test
    void 게시글_삭제_API_테스트() throws Exception {
        // When & Then
        mockMvc.perform(delete("/boards/" + savedBoard.getId()))
                .andExpect(status().isNoContent());
    }
}
