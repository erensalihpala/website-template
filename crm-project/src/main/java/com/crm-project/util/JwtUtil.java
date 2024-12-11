package com.crm-project.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtUtil
 * 
 * JWT oluşturma ve doğrulama işlemlerini yönetir.
 */
@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secret_key"; // Güvenli bir key seçin

    /**
     * JWT Token oluşturur.
     * 
     * @param username Kullanıcı adı
     * @return JWT token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerli
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * JWT Token içerisindeki kullanıcı adını döndürür.
     * 
     * @param token JWT token
     * @return Kullanıcı adı
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * JWT Token geçerliliğini kontrol eder.
     * 
     * @param token JWT token
     * @param username Kullanıcı adı
     * @return Token geçerli mi
     */
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Token süresinin dolup dolmadığını kontrol eder.
     * 
     * @param token JWT token
     * @return Süresi dolmuş mu
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
