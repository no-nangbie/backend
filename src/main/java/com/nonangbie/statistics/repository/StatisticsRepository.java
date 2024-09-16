package com.nonangbie.statistics.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.statistics.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByMember(Member member);
}
