package com.nonangbie.email.tool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailTool {
    private final JavaMailSender javaMailSender;

    public void sendHtmlEmail(String email, String title, String htmlContent) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(htmlContent, true); // true means it's HTML

            javaMailSender.send(mimeMessage);
            log.info("HTML 이메일 발송 성공");
        } catch (MessagingException e) {
            log.error("HTML 이메일 발송 오류", e);
        }
    }
    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
