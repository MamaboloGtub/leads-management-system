package com.mamabologtub.leads_management_system.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

/**
 * @Author Tshepo M Mahudu on Oct 19, 2025.
 */

@Component
public class JwtUtil {

    private final Key key;
    private final long expirationMs;

    public JwtUtil(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(now)
                .expiration(exp)
                .and()
                .signWith(key)
                .compact();
    }

    public Mono<String> validateAndGetUsername(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return Mono.just(claims.getBody().getSubject());
        } catch (JwtException e) {
            return Mono.empty();
        }
    }

}
