package com.jj.social.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption; // 사진 설명

    private String postImgUrl; // 이미지 저장 경로

    private String oriImgName;

    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "image")
    @JsonIgnore
    @BatchSize(size = 50)
    private List<Comment> comments;

    @OneToMany(mappedBy = "image")
    @JsonIgnore
    @BatchSize(size = 50)
    private List<Likes> likes;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
