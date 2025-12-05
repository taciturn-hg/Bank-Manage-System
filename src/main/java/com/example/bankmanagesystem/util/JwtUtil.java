package com.example.bankmanagesystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "my-super-secret-key-123456789012345";  // >=32 字节
    private static final long EXPIRE = 1000 * 60 * 60 * 24; // 24h

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /** 生成 token —— JJWT 0.12 新写法 */
    public String generateToken(Long userId, String username, String role) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(getKey(), Jwts.SIG.HS256)  // JJWT 0.12 必须用 SIG
                .compact();
    }

    /** 解析 token —— JJWT 0.12 新写法 */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())  // verifyWith(Key)
                .build()
                .parseSignedClaims(token)
                .getPayload();          // 返回 Claims
    }

    /** 提供给 Security 用 */
    public String extractUsername(String token) {
        return parseToken(token).get("username", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return Long.parseLong(parseToken(token).getSubject());
    }

    public String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }
}
