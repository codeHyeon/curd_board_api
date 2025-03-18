package me.codehyeon.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.codehyeon.board.entity.Board;

@Getter
@NoArgsConstructor
public class BoardResponseDto { // 이미 저장된 것에서 가져오는 데이터이므로 따로 유효성 검증 하지 않음
    private Long id;
    private String title;
    private String content;

    // board 정보 받아서 responseDto 정보 생성
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}

