package com.nonangbie.auth.controller;

import com.nonangbie.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 로그아웃을 처리하는 Post요청
     * @param authentication
     * @return HTTP 응답 반환
     */
    @PostMapping("/logout")
    public ResponseEntity postLogout(Authentication authentication) {
        String email = authentication.getName();
        return authService.logout(email) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
