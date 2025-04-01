package com.cbox.kioskservice.api.common.util;


import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

import javax.crypto.SecretKey;

import org.springframework.security.oauth2.jwt.JwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Log4j2
public class JWTUtil {


     private static final String SECRET_KEY = "1234567890123456789012345678901234567890";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Map<String, Object> valueMap, int min) {
        try {
            return Jwts.builder()
                    .setHeader(Map.of("typ", "JWT"))
                    .setClaims(valueMap)
                    .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                    .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                    .signWith(KEY)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating JWT token", e);
            throw new RuntimeException("JWT Generation Error");
        }
    }

    public static Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT Expired", e);
            throw new CustomJWTException("Expired");
        } catch (MalformedJwtException e) {
            log.error("JWT Malformed", e);
            throw new CustomJWTException("MalFormed");
        } catch (InvalidClaimException e) {
            log.error("JWT Invalid Claims", e);
            throw new CustomJWTException("Invalid");
        } catch (JwtException e) {
            log.error("JWT Error", e);
            throw new CustomJWTException("JWTError");
        } catch (Exception e) {
            log.error("Unknown JWT Error", e);
            throw new CustomJWTException("Error");
        }
    }

}
