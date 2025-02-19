package com.jj.social.dto.image;

import java.util.List;

public interface ImageMainDto {
    Long getImageId();
    String getImgName();
    String getCaption();

    List<CommentsDto> getComments();

    interface CommentsDto {
        String getContent();
        UserDto getUser();
    }

    interface UserDto{
        String getNickname();
        Long getId();
    }

}
