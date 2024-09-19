package com.nonangbie.member.service;

import com.nonangbie.auth.utils.CustomAuthorityUtils;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.dto.MemberDto;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.statistics.service.StatisticsService;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService extends ExtractMemberEmail {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final StatisticsService statisticsService;

    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        verifyNickName(member.getNickname());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        Member savedMember = memberRepository.save(member);
        List<Statistics> statisticsList = statisticsService.createStatistics(savedMember);
        member.setStatisticsList(statisticsList);
//        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    @Transactional(readOnly = true)
    public Member findMember(Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        return member;
    }

    public Page<Member> findMembers(int page, int size, Authentication authentication) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public Member updateMember(Member member, Authentication authentication) {
        Member authenticatedMember = extractMemberFromAuthentication(authentication,memberRepository);

        // 비밀번호가 null이 아니고, 기존 비밀번호와 다를 경우에만 업데이트
        if (member.getPassword() != null && !passwordEncoder.matches(member.getPassword(), authenticatedMember.getPassword())) {
            String encryptedPassword = passwordEncoder.encode(member.getPassword()); // 비밀번호 암호화
            authenticatedMember.setPassword(encryptedPassword); // 암호화된 비밀번호로 설정
        }

        // 닉네임 업데이트
        if (member.getNickname() != null && !member.getNickname().equals(authenticatedMember.getNickname())) {
            authenticatedMember.setNickname(member.getNickname());
        }

        return memberRepository.save(authenticatedMember);
    }

    public Member updatePassword(String email, MemberDto.UpdatePassword updatePasswordDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), member.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
        }

        String newEncryptedPassword = passwordEncoder.encode(updatePasswordDto.getNewPassword());
        member.setPassword(newEncryptedPassword);
        return memberRepository.save(member);
    }


    public Member findMemberId(Member member) {
        Optional<Member> verifiedMember =
                memberRepository.findByEmail(member.getEmail());
        Member findedMember = verifiedMember
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findedMember;
    }

    /**
     * password찾기
     * @param member
     * @return
     */
    public Member findMemberPassWord(Member member) {
        memberRepository.findByEmailAndNickname(member.getEmail(), member.getNickname());
        return member;
    }

    public void deleteMember(Authentication authentication) {
        Member authenticatedMember = extractMemberFromAuthentication(authentication,memberRepository);
        memberRepository.delete(authenticatedMember);
    }

    private void verifyExistMember(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    public Member findVerifiedMembers(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember = optionalMember.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyNickName(String nickName) {
        Optional<Member> member = memberRepository.findByNickname(nickName);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }
    // 닉네임 중복 여부 확인 메서드
    @Transactional(readOnly = true)
    public boolean checkNicknameExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
