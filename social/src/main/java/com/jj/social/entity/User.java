package com.jj.social.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(nullable = false)
    private String nickname; // 별명

    @Column(nullable = false)
    private String email;

    private String website;

    private String bio;

    private String phone;

    private String gender;

    private String profileImageUri;

    private String role;

    private LocalDateTime createDate;

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

}
