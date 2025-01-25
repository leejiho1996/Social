package com.jj.social.dto;

import com.jj.social.entity.Image;
import com.jj.social.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {
    private boolean PageOwnerState; // 계정 소유자 여부

    private Integer imageCount; // 게시글 수

    private boolean subscribeState; // 구독 상태

    private Integer subscribeCount; // 구독자 수

    private Long userId;

    private String username; // 아이디

    private String nickname; // 별명

    private String website;

    private String bio;

    private String profileImageUri;

    private final List<Image> imageList;

    public UserProfileDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.imageList = user.getImages();
        this.website = user.getWebsite();
        this.bio = user.getBio();
        this.profileImageUri = user.getProfileImageUri();
    }
}
