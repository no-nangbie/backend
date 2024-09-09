package com.nonangbie.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenizer {
    // RedisTemplate을 사용하여 Redis 서버와의 상호작용을 처리하는 필드를 선언.
    private final RedisTemplate<String, Object> redisTemplate;
    // 의존성 주입
    public JwtTokenizer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Getter
    @Value("bGS62Cs9ZELwmdY0hu9f0q/TwYLypqvwZz832mR0wM0=")
    private String secretKey;

    @Getter
    @Value("60")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("600")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        // Redis의 ListOperations 객체를 사용하여 리스트 형태로 데이터를 처리.
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // claims에 저장된 username(이메일)을 키로 accessToken 값을 추가.
        valueOperations.set((String) claims.get("username"), accessToken, accessTokenExpirationMinutes, TimeUnit.MINUTES);
        return accessToken;
    }

    // redis에 accessToken을 키로 사용하기 위해, accessToken을 함께 전달받습니다.
    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String base64EncodedSecretKey,
                                       String accessToken) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        String refreshToken = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        // Redis의 ListOperations 객체를 사용하여 리스트 형태로 데이터를 처리.
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // "accessToken"이라는 키에 accessToken 값을 리스트에 추가.
        valueOperations.set(accessToken, refreshToken, refreshTokenExpirationMinutes, TimeUnit.MINUTES);

        return refreshToken;
    }

    // 검증 후, Claims을 반환 하는 용도
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    // 단순히 검증만 하는 용도로 쓰일 경우
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    /**
     * getTokenExpiration의 설명을 여기에 작성한다.
     *
     * @param 발행일 기준 만료일 설정 시간
     * @return 만료일 계산 결과
     */
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

//    // 로그아웃시 레디스에서 email를 기준으로 토큰 값 삭제
    public boolean deleteRegisterToken(String username) {
        return Optional.ofNullable(redisTemplate.hasKey(username))
                .filter(Boolean::booleanValue) // 키가 존재할 때만 진행
                .map(hasKey -> {
                    String accessToken = (String) redisTemplate.opsForValue().get(username);
                    redisTemplate.delete(accessToken);
                    redisTemplate.delete(username);
                    return true;
                })
                .orElse(false); // 키가 존재하지 않거나 삭제되지 않았을 때 false 반환
    }
}