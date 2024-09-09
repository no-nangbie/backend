package com.nonangbie.utils;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;

public abstract class ExtractMemberEmail {

    public Member extractMemberFromAuthentication(Authentication authentication,
                                                  MemberRepository memberRepository)  {

        String username = (String) authentication.getPrincipal();

        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
