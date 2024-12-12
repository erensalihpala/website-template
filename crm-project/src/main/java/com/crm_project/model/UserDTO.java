package com.crm_project.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "Kullanıcı adı boş olamaz")
    private String username;

    @NotEmpty(message = "Şifre boş olamaz")
    private String password;
}
