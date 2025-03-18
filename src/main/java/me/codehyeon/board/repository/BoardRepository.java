package me.codehyeon.board.repository;

import me.codehyeon.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // Board findByName(Long id);
}


