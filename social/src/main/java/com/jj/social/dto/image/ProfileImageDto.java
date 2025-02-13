package com.jj.social.dto.image;

import lombok.Data;

@Data
public class ProfileImageDto {
    private long id; // 이미지 아이디

    private String imgName;

    private long likeCount; // 좋아요 갯수

    public ProfileImageDto(long id, String imgName, long likeCount) {
        this.id = id;
        this.imgName = imgName;
        this.likeCount = likeCount;
    }
}
