package com.jj.social.dto;

import com.jj.social.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {
    @Size(min = 2, max = 10)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;
    private String role;

    public User toEntity() {
        return User
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(name)
                .role(role)
                .build();
    }

}
