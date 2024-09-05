package com.nonangbie.member.controller;

import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.member.dto.MemberDto;
import com.nonangbie.member.dto.VerificationRequest;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.mapper.MemberMapper;
import com.nonangbie.member.service.MemberService;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService service;
    private final MemberMapper memberMapper;
//    private final EmailVerificationService emailVerificationService;
//    private final AuthService authService;

    // 이메일 인증코드 전송
//    @PostMapping("/auth-code")
//    public ResponseEntity signUpMember(@Valid @RequestBody VerificationRequest verificationRequest) {
//        emailVerificationService.sendCodeToEmail(verificationRequest.getEmail());
//
//        return ResponseEntity.accepted().body("이메일로 인증 코드를 전송했습니다. 인증 코드를 입력하여 회원가입을 완료하세요.");
//    }
//
//    // 인증코드 검증
//    @PostMapping("/verify-auth-code")
//    public ResponseEntity verifyEmail(@Valid @RequestBody VerificationRequest verificationRequest) {
//        String email = verificationRequest.getEmail();
//        String authCode = verificationRequest.getAuthCode();
//
//        boolean isVerified = emailVerificationService.verifyCode(email, authCode);
//        if (!isVerified) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 올바르지 않습니다.");
//        }
//        return ResponseEntity.ok("이메일 인증이 완료되었습니다. 회원가입을 진행하세요.");
//    }

    // 회원가입 완료
    @PostMapping("/members")
    public ResponseEntity createMember(@Valid @RequestBody MemberDto.Post memberDto, BindingResult bindingResult) {
        // 유효성 검사 후 에러가 있으면 처리
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Member member = memberMapper.memberPostToMember(memberDto);
        service.createMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/members/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
//        String email = null;
//        if (authentication != null) {
//            email = (String) authentication.getPrincipal();
//            boolean isLoggedOut = !authService.isTokenValid(email);
//            if (isLoggedOut) {
//                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        Member member = service.findMember(memberId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {
//        String email = null;
//        if (authentication != null) {
//            email = (String) authentication.getPrincipal();
//            boolean isLoggedOut = !authService.isTokenValid(email);
//            if (isLoggedOut) {
//                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        Page<Member> pageMembers = service.findMembers(page -1, size);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(memberMapper.membersToResponseDto(members), pageMembers), HttpStatus.OK);
    }

    @PatchMapping("/members/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id")@Positive long memberId,
                                          @Valid @RequestBody MemberDto.Patch patch,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
//        String email = null;
//        if (authentication != null) {
//            email = (String) authentication.getPrincipal();
//            boolean isLoggedOut = !authService.isTokenValid(email);
//            if (isLoggedOut) {
//                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        patch.setMemberId(memberId);
        Member member = service.updateMember(memberMapper.memberPatchToMember(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @DeleteMapping("/members/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
//        String email = null;
//        if (authentication != null) {
//            email = (String) authentication.getPrincipal();
//            boolean isLoggedOut = !authService.isTokenValid(email);
//            if (isLoggedOut) {
//                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        service.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
