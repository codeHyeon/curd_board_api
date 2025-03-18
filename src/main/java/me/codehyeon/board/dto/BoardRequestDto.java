package me.codehyeon.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 테스트용
public class BoardRequestDto {          // 받을 데이터 + 유효성 검증
        @NotNull(message = "제목은 필수 입력 값입니다.")
        private String title;

        private String content;
}