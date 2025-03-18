package me.codehyeon.board.controller;

import jakarta.validation.Valid;
import me.codehyeon.board.dto.BoardRequestDto;
import me.codehyeon.board.dto.BoardResponseDto;
import me.codehyeon.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     // json 이용
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto responseDto = boardService.createBoard(boardRequestDto);
        return ResponseEntity.status(201).body(responseDto);
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        List<BoardResponseDto> boards = boardService.getAllBoards();
        return ResponseEntity.status(200).body(boards);
        // return ResponseEntity.status(HttpStatus.OK).body(boards);
        // return ResponseEntity.ok(boards);
    }

    // 게시글 조회 (ID로)
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable(name = "id") Long id) {
        BoardResponseDto board = boardService.getBoardById(id);
        return ResponseEntity.status(200).body(board);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable(name = "id") Long id, @RequestBody @Valid BoardRequestDto boardRequestDto) {
        BoardResponseDto updatedBoard = boardService.updateBoard(id, boardRequestDto);
        return ResponseEntity.status(201).body(updatedBoard);
        // return ResponseEntity.status(HttpStatus.CREATED).body(updatedBoard);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
        // return ResponseEntity.status(204).build();
    }
}
