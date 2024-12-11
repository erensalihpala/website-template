package com.crm-project.controller;

import com.crm-project.model.AuthRequest;
import com.crm-project.model.AuthResponse;
import com.crm-project.model.User;
import com.crm-project.service.UserService;
import com.crm-project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
 * 
 * Kullanıcı kayıt ve giriş işlemlerini yönetir.
 * - Kayıt API'si: Yeni kullanıcıların kayıt edilmesini sağlar.
 * - Giriş API'si: Kullanıcıların doğrulanmasını ve token oluşturulmasını sağlar.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Yeni bir kullanıcı kaydı API'si
     * 
     * @param user Kaydedilecek kullanıcı verileri (JSON formatında)
     * @return Kaydedilen kullanıcı veya hata mesajı
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Kullanıcı giriş API'si
     * 
     * @param authRequest Kullanıcı adı ve şifre bilgileri (JSON formatında)
     * @return Başarılı giriş durumunda JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            // Kullanıcı doğrulaması
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // Kullanıcı doğrulandı, token oluştur
            final String jwt = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Geçersiz kullanıcı adı veya şifre.");
        }
    }
}
