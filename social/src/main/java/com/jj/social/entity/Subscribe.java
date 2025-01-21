package com.jj.social.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// unique 제약 조건
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {
                                "fromUserId",
                                "toUserId"
                        }
                )
        }
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private User fromUser; // 구독자

    @ManyToOne
    @JoinColumn(name = "toUserId")
    private User toUser; // 피구독자

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
