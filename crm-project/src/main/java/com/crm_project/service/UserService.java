/*
 * Bu metot yeni bir kullanıcıyı kaydeder.
 * Bu Dosyanın Amacı:
 * Kullanıcı Kayıt İşlemi: Kullanıcı adının benzersiz olduğunu kontrol eder ve yeni bir kullanıcıyı veritabanına kaydeder.
 * Şifreleme: Kullanıcı şifresini güvenli bir şekilde BCryptPasswordEncoder ile şifreler.
 * Kullanıcı Bulma: Kullanıcı giriş işlemlerinde veya yetkilendirme aşamasında kullanıcı bilgilerini getirir.
 */

package com.crm_project.service;

import com.crm_project.model.User;
import com.crm_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Kullanıcı kaydı
    public User registerUser(User user) {
        // Kullanıcı adı kontrolü
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Kullanıcı adı zaten mevcut!");
        }
        // Şifreyi şifrele ve kaydet
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Varsayılan rol
        return userRepository.save(user);
    }

    // Kullanıcı yükleme (Spring Security için)
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }
}