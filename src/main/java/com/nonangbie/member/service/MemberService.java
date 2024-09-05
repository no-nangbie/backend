package com.nonangbie.member.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        verifyNickName(member.getNickname());
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        // 비밀번호가 null이 아니고, 기존 비밀번호와 다를 경우에만 업데이트
        if (member.getPassword() != null && !member.getPassword().equals(findMember.getPassword())) {
            findMember.setPassword(member.getPassword()); // 평문 비밀번호 설정 (암호화 X)
        }

        // 닉네임 업데이트
        if (member.getNickname() != null && !member.getNickname().equals(findMember.getNickname())) {
            findMember.setNickname(member.getNickname());
        }
        return memberRepository.save(findMember);
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

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
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

    private void verifyNickName(String nickName) {
        Optional<Member> member = memberRepository.findByNickname(nickName);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }
    }

//    private Member extractMemberFromAuthentication(Authentication authentication) {
//        /**
//         * 첫 번째 if 블록에서는 메서드로 전달된 authentication 객체가 null인 경우,
//         * SecurityContextHolder에서 인증 정보를 가져오려고 시도.
//         * 두 번째 if 블록에서는 authentication이 여전히 null인 경우,
//         * 사용자에게 인증되지 않았음을 알리고, 처리할 수 있도록 예외를 발생시킴.
//         */
////        if (authentication == null) {
////            authentication = SecurityContextHolder.getContext().getAuthentication();
////        }
////
////        if (authentication == null) {
////            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
////        }
//
//        String username = (String) authentication.getPrincipal();
//
//        return memberRepository.findByEmail(username)
//                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//    }
}
