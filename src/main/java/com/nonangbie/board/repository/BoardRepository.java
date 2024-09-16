package com.nonangbie.board.repository;

import com.nonangbie.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByMenuCategory(Board.MenuCategory type, Pageable pageable);

    Page<Board> findAllByMenuCategoryAndBoardIdIn(Board.MenuCategory type, List<Long> ids, Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    Page<Board> findAllByBoardIdIn(List<Long> ids, Pageable pageable);
}