package com.jj.social.dto.comment;

import lombok.Data;

@Data
public class CommentStoryDto {
    // comment 정보
    private Long id;
    private String content;

    // 유저 정보
    private Long userId;
    private String nickname;

    // 이미지 정보
    private Long imageId;

    public CommentStoryDto(Long id, String content, Long userId, String nickname, Long imageId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.nickname = nickname;
        this.imageId = imageId;
    }
}
