package me.codehyeon.board.service;

import jakarta.transaction.Transactional;
import me.codehyeon.board.dto.BoardRequestDto;
import me.codehyeon.board.dto.BoardResponseDto;
import me.codehyeon.board.entity.Board;
import me.codehyeon.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 작성
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        board = boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    // 모든 게시글 조회
    public List<BoardResponseDto> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 게시글 조회 (ID로)
    public BoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID : " + id));
        return new BoardResponseDto(board);
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID : " + id));
        // board.setTitle(boardRequestDto.getTitle());
        // board.setContent(boardRequestDto.getContent());
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: " + id);
        }
        boardRepository.deleteById(id);
    }
}
