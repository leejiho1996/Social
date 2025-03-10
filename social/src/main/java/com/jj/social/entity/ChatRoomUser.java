package com.jj.social.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChatRoomUser {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom room;
}
