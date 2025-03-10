package com.jj.social.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    private String userName;

    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User user;
}
