package com.springboot.hanbandobe.domain.board.repository;

import com.springboot.hanbandobe.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    Page<Board> findBoardsByBoardCategory_BoardCategoryNo(Pageable pageable, Long boardCategoryNo);

    Page<Board> findBoardsByBoardCategory_BoardCategoryNoAndTitleContains(Pageable pageable, Long boardCategoryNo, String boardTitle);
}
