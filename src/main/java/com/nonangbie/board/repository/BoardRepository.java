package com.nonangbie.board.repository;

import com.nonangbie.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE "
            + "(:menuCategory IS NULL OR b.menuCategory = :menuCategory) AND "
            + "(:keyword IS NULL OR b.title LIKE %:keyword%) AND "
            + "(:ids IS NULL OR b.boardId IN :ids)")
    Page<Board> findBoards(
            @Param("menuCategory") Board.MenuCategory menuCategory,
            @Param("keyword") String keyword,
            @Param("ids") List<Long> ids,
            Pageable pageable
    );
    @Query("SELECT b FROM Board b WHERE "
            + "(:menuCategory IS NULL OR b.menuCategory = :menuCategory) AND "
            + "(:ids IS NULL OR b.boardId IN :ids)")
    Page<Board> findBoardsNoKeyword(
            @Param("menuCategory") Board.MenuCategory menuCategory,
            @Param("ids") List<Long> ids,
            Pageable pageable
    );
}