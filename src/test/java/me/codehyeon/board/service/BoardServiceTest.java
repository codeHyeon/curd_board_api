package me.codehyeon.board.service;

import me.codehyeon.board.dto.BoardRequestDto;
import me.codehyeon.board.dto.BoardResponseDto;
import me.codehyeon.board.entity.Board;
import me.codehyeon.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional  // 테스트 후 롤백
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 게시글_저장_테스트() {
        // Given
        BoardRequestDto requestDto = new BoardRequestDto("테스트 제목", "테스트 내용");

        // When
        BoardResponseDto responseDto = boardService.createBoard(requestDto);

        // Then
        assertThat(responseDto.getTitle()).isEqualTo("테스트 제목");
        assertThat(responseDto.getContent()).isEqualTo("테스트 내용");

        // DB에 저장되었는지 확인
        List<Board> boards = boardRepository.findAll();
        assertThat(boards).hasSize(1);
        assertThat(boards.get(0).getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    void 게시글_수정_테스트() {
        // Given
        BoardRequestDto requestDto = new BoardRequestDto("제목1", "내용1");
        BoardResponseDto createdBoard = boardService.createBoard(requestDto);

        // When
        BoardRequestDto updateDto = new BoardRequestDto("수정된 제목", "수정된 내용");
        BoardResponseDto updatedBoard = boardService.updateBoard(createdBoard.getId(), updateDto);

        // Then
        assertThat(updatedBoard.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedBoard.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    void 게시글_삭제_테스트() {
        // Given
        BoardRequestDto requestDto = new BoardRequestDto("삭제 테스트", "내용");
        BoardResponseDto createdBoard = boardService.createBoard(requestDto);

        // When
        boardService.deleteBoard(createdBoard.getId());

        // Then
        assertThat(boardRepository.existsById(createdBoard.getId())).isFalse();
    }

    @Test
    void 모든_게시글_조회_테스트() {
        // Given
        boardService.createBoard(new BoardRequestDto("제목1", "내용1"));
        boardService.createBoard(new BoardRequestDto("제목2", "내용2"));

        // When
        List<BoardResponseDto> boards = boardService.getAllBoards();

        // Then
        assertThat(boards).hasSize(2);
    }
}
