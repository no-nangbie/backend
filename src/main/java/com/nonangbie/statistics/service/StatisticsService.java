package com.nonangbie.statistics.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.statistics.repository.StatisticsRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService extends ExtractMemberEmail {
    private final StatisticsRepository repository;
    private final MemberRepository memberRepository;



    public void updateStatistics(String menuCategory, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        Statistics findStatistics = repository.findByMember(member)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));

    }
}
