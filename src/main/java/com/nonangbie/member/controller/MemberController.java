package com.nonangbie.member.controller;

import com.nonangbie.auth.service.AuthService;
import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.email.service.EmailVerificationService;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService service;
    private final MemberMapper memberMapper;
    private final EmailVerificationService emailVerificationService;
    private final AuthService authService;

     //이메일 인증코드 전송
    @PostMapping("/auth-code")
    public ResponseEntity signUpMember(@Valid @RequestBody VerificationRequest verificationRequest) {
        emailVerificationService.sendCodeToEmail(verificationRequest.getEmail());

        return ResponseEntity.accepted().body("이메일로 인증 코드를 전송했습니다. 인증 코드를 입력하여 회원가입을 완료하세요.");
    }

     //인증코드 검증
    @PostMapping("/verify-auth-code")
    public ResponseEntity verifyEmail(@Valid @RequestBody VerificationRequest verificationRequest) {
        String email = verificationRequest.getEmail();
        String authCode = verificationRequest.getAuthCode();

        boolean isVerified = emailVerificationService.verifyCode(email, authCode);
        if (!isVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 올바르지 않습니다.");
        }
        return ResponseEntity.ok("이메일 인증이 완료되었습니다. 회원가입을 진행하세요.");
    }

    // 회원가입 완료
    @PostMapping("/signup")
    public ResponseEntity createMember(@Valid @RequestBody MemberDto.Post memberDto, BindingResult bindingResult) {
        // 유효성 검사 후 에러가 있으면 처리
        if (bindingResult.hasErrors()) {
            // 유효성 검사에서 발생한 오류 메시지들을 반환
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Member member = memberMapper.memberPostToMember(memberDto);
        service.createMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/member")
    public ResponseEntity getMember(Authentication authentication) {
        String email = null;
        if (authentication != null) {
            email = (String) authentication.getPrincipal();
            boolean isLoggedOut = !authService.isTokenValid(email);
            if (isLoggedOut) {
                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Member member = service.findMember(authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,Authentication authentication) {
        String email = null;
        if (authentication != null) {
            email = (String) authentication.getPrincipal();
            boolean isLoggedOut = !authService.isTokenValid(email);
            if (isLoggedOut) {
                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Page<Member> pageMembers = service.findMembers(page -1, size,authentication);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(memberMapper.membersToResponseDto(members), pageMembers), HttpStatus.OK);
    }

    @PatchMapping("/members")
    public ResponseEntity patchMember(Authentication authentication,
                                          @Valid @RequestBody MemberDto.Patch patch,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        String email = null;
        if (authentication != null) {
            email = (String) authentication.getPrincipal();
            boolean isLoggedOut = !authService.isTokenValid(email);
            if (isLoggedOut) {
                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Member member = service.updateMember(memberMapper.memberPatchToMember(patch),authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToResponseDto(member)), HttpStatus.OK);
    }

    @DeleteMapping("/members")
    public ResponseEntity deleteMember(Authentication authentication) {
        String email = null;
        if (authentication != null) {
            email = (String) authentication.getPrincipal();
            boolean isLoggedOut = !authService.isTokenValid(email);
            if (isLoggedOut) {
                return new ResponseEntity<>("User Logged Out", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        service.deleteMember(authentication);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // 닉네임 중복 확인 API 추가
    @GetMapping("/nickname-check")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        boolean exists = service.checkNicknameExists(nickname);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NickName exists");
        } else {
            return ResponseEntity.ok("NickName available");
        }
    }
}
