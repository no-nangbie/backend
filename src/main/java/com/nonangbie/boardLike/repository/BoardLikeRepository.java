package com.nonangbie.boardLike.repository;

import com.nonangbie.boardLike.entity.BoardLike;
import com.nonangbie.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>{
    Optional<BoardLike> findByMember(Member member);
}
