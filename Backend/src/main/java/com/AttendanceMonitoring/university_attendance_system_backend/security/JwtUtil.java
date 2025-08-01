package com.AttendanceMonitoring.university_attendance_system_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key key;
    private final JwtParser parser;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parser()
                .setSigningKey(key)
                .build();
    }

    public String generateToken(Map<String, Object> claims, String subject, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(nowMillis + expirationMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = Map.of("role", role);
        long expirationMillis = 1000 *30*60 ; // 1 hour
        return generateToken(claims, username, expirationMillis);
    }   

    public Claims extractClaims(String token) {
        try {
            Jws<Claims> jwsClaims = parser.parseClaimsJws(token);
            return jwsClaims.getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
