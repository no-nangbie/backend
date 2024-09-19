package com.nonangbie.email.service;

import com.nonangbie.email.tool.EmailTool;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.redis.tool.RedisTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationService {
    private final MemberRepository memberRepository;
    private final RedisTool redisTool;
    private final EmailTool emailTool;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("111222")
    private String superCode;

    @Value("600000")
    private long authCodeExpirationMillis;

    // 이메일로 인증 코드를 전송하는 메서드
    public void sendCodeToEmail(String toEmail) {
        //이메일 중복 검사
        if(memberRepository.existsByEmail(toEmail)) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);

        //인증코드 생성
        String title = "유저 이메일 인증 번호";
        String authCode = this.createCode();

        //더미계정생성을 위한 authCode 노출
        System.out.println("*".repeat(30));
        System.out.println("Email authCode : " + authCode);
        System.out.println("*".repeat(30));

        // Redis에 인증 코드를 저장, 설정된 시간(authCodeExpirationMillis) 이후에 자동으로 만료됨
        redisTool.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
        // 인증 코드를 사용자의 이메일로 전송
        // 인증 코드를 사용자의 이메일로 전송
        // HTML 이메일 본문 생성
        // HTML 이메일 본문 생성
        String emailBody = "<div style='font-family: Arial, sans-serif; background-color: #EBFBFF; padding: 60px; text-align: center;'>"
                + "<div style='background-color: #ffffff; border-radius: 3px; padding: 60px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); max-width: 800px; margin: auto;'>"
                + "<img src='https://no-nangbie-20240912.s3.ap-northeast-2.amazonaws.com/logo.png' alt='Logo' style='width: 100px; height: auto; margin-bottom: 30px;' />"
                + "<h1 style='color: #2c2f33; margin-bottom: 40px;'>이메일 인증</h1>"
                + "<p style='color: #555; font-size: 13px;'>다음은 인증번호입니다</p>"
                + "<h2 style='color: #7289da; font-size: 24px;'>" + authCode + "</h2>"
                + "<p style='color: #555; font-size: 13px;'>해당 인증 번호를 이용해 이메일 인증을 완료해주세요.</p>"
                + "</div></div>";




// 인증 코드를 사용자의 이메일로 전송
        emailTool.sendHtmlEmail(toEmail, title, emailBody);



    }

    // 사용자가 제출한 인증 코드가 맞는지 검증하는 메서드
    public boolean verifyCode(String email, String authCode) {
        String redisAuthCode = redisTool.getValues(AUTH_CODE_PREFIX + email);

        return redisTool.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessLogicException(ExceptionCode.UNABLE_TO_SEND_EMAIL);
        }
    }

    public boolean verifySuperCode(String email, String inputCode) {
        if(superCode.equals(inputCode)) {
            return true;
        }

        String redisAuthCode = redisTool.getValues(AUTH_CODE_PREFIX + email);

        return redisTool.checkExistsValue(redisAuthCode) && redisAuthCode.equals(inputCode);
    }
}