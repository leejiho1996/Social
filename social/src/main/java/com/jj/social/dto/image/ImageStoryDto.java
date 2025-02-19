package com.jj.social.dto.image;

import com.jj.social.dto.comment.CommentStoryDto;
import com.jj.social.entity.Image;
import com.jj.social.entity.User;
import lombok.Data;

import java.util.List;

@Data
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

    private List<CommentStoryDto> commentList;

    public ImageStoryDto(Image image, User user, int likeCount, boolean likeState) {
        this.id = image.getId();
        this.caption = image.getCaption();
        this.oriImgName = image.getOriImgName();
        this.imgName = image.getImgName();
        this.userId = user.getId();
        this.username = user.getUsername();
        this.profileImageUri = user.getProfileImageUri();
        this.likeCount = likeCount;
        this.likeState = likeState;
    }
}
