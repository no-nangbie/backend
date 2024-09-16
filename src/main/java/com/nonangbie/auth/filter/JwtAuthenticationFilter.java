package com.nonangbie.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonangbie.auth.dto.LoginDto;
import com.nonangbie.auth.jwt.JwtTokenizer;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
    * 사용자가 로그인 할 때 실행되는 메서드, 사용자의 email과 비밀번호를 읽어와서 token을 만든 후 사용자가 맞는지 판단.
    */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        // 사용자 이메일로 데이터베이스에서 사용자 정보 조회
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found") {});

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new AuthenticationException("Invalid password") {};
        }

        // 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
    * 인증이 성공한 경우 실행되는 메서드
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();

        // Access Token 및 Refresh Token 생성
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member, accessToken);

        // 응답 헤더에 토큰 추가
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        // 성공 핸들러 호출
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);  // 추가
    }

    /**
    * AccessToken 생성하는 메서드
    */
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("memberId", member.getMemberId());
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    /**
     * RefreshToken 생성하는 메서드
     */
    private String delegateRefreshToken(Member member, String accessToken) {
        // 수정
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());

        // 수정
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey, accessToken);

        return refreshToken;
    }
}