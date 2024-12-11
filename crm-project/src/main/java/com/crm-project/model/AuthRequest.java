package com.crm-project.model;

/**
 * AuthRequest
 * 
 * Giriş işlemi için kullanıcıdan alınan bilgileri temsil eder.
 */
public class AuthRequest {
    private String username;
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
