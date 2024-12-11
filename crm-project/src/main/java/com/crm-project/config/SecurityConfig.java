package com.crm-project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 * 
 * Uygulamanın güvenlik yapılandırmasını sağlar.
 * - Yetkilendirme ve kimlik doğrulama mekanizmalarını yönetir.
 * - BCrypt ile şifreleme.
 * - API erişim kurallarını belirler.
 */
@Configuration
public class SecurityConfig {

    /**
     * BCryptPasswordEncoder
     * 
     * Kullanıcı şifrelerini güvenli bir şekilde şifrelemek için BCrypt algoritması kullanılır.
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SecurityFilterChain
     * 
     * HTTP güvenlik kurallarını yapılandırır.
     * - Yetkisiz kullanıcıların erişim kurallarını tanımlar.
     * 
     * @param http HttpSecurity yapılandırması
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll() // Auth endpoint'lerine izin ver
            .anyRequest().authenticated(); // Diğer tüm endpoint'ler için yetki gerektir
        return http.build();
    }

    /**
     * AuthenticationManager Bean
     * 
     * Kimlik doğrulama için AuthenticationManager sağlar.
     * 
     * @param auth AuthenticationManagerBuilder
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        return auth.build();
    }
}

@Autowired
private JwtRequestFilter jwtRequestFilter;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/auth/**").permitAll() // Yetkilendirme gerektirmeyen endpoint'ler
        .anyRequest().authenticated() // Diğer tüm endpoint'ler korumalı
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi ekleniyor
    return http.build();
}
