package me.codehyeon.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.codehyeon.board.dto.BoardRequestDto;

@Getter     // Getter 자동 생성
// @Setter     // Setter 자동 생성
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 글 고유 아이디

    @Column(nullable = false)
    private String title;   // 글 제목 (빈 제목 불가)

    private String content; // 글 내용 (빈 내용 가능)

    // 게시글 작성 시 받을 데이터만 정의
    public Board(BoardRequestDto BoardDto) {
        this.title = BoardDto.getTitle();
        this.content = BoardDto.getContent();
    }

    // setter 대신 update 메서드 생성해서 사용
    public void update(BoardRequestDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }

}
