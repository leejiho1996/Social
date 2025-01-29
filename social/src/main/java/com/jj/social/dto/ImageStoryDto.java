package com.jj.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageStoryDto {

    private Long id;

    private String caption; // 사진 설명

    private String oriImgName;

    private String imgName;

    private Long userId;

    private String username;

    private String profileImageUri;

    private Integer likeCount = 0;

    private Boolean likeState = false;


}
