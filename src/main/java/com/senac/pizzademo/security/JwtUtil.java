package com.senac.pizzademo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

public class JwtUtil {
    // Private constructor to prevent instantiation
    private JwtUtil() {}

    // Gere uma chave secreta com pelo menos 512 bits (64 bytes) para HS512
    private static final String SECRET_KEY = "umasecretkeymuitograndeparausohs512umasecretkeymuitograndeparausohs5121234567890";
    private static final long EXPIRATION_TIME = 86400000;

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(KEY, SignatureAlgorithm.HS512)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
