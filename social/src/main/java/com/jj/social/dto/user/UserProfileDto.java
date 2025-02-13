package com.jj.social.dto.user;

import com.jj.social.dto.image.ProfileImageDto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {
    private boolean PageOwnerState; // 계정 소유자 여부

    private Integer imageCount; // 게시글 수

    private Integer subscribeState; // 구독 상태

    private Long subscribeCount; // 구독자 수

    private Long userId;

    private String username; // 아이디

    private String nickname; // 별명

    private String website;

    private String bio;

    private String profileImageUri;

    private List<ProfileImageDto> imageList;

    public UserProfileDto(Long userId, String bio, String nickname,
                          String profileImageUri, String username, String website,
                          Long subscribeCount, Integer subscribeState) {
        this.userId = userId;
        this.bio = bio;
        this.nickname = nickname;
        this.profileImageUri = profileImageUri;
        this.username = username;
        this.website = website;
        this.subscribeCount = subscribeCount;
        this.subscribeState = subscribeState;
    }
}
