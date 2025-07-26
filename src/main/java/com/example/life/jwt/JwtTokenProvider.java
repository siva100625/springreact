package com.example.life.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "ThisIsMyVerySecureAndLongJwtSecretUsedOnlyForSigningTokensInSpringBootAndForItsSecurity";
    private final long jwtExpirationMs = 86400000; // 1 day

    public String generateToken(String email, Set<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles.stream().collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
