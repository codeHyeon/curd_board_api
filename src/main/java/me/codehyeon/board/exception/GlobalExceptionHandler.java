package me.codehyeon.board.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 게시글 조회 or 수정 시 발생하는 예외 처리 (상태 코드 : 404)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회 실패: " + ex.getMessage());
    }

    // DB 무결성 위반 예외 처리 (예: NOT NULL 필드 위반) (상태 코드 : 400)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("데이터 무결성 오류: " + ex.getMessage());
    }

    // 기타 예상하지 못한 예외 처리 (상태 코드 : 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + ex.getMessage());
    }
}
