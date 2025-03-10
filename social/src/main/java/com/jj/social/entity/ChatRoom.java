package com.jj.social.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String lastMessage;
}
