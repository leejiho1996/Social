package com.jj.social.dto.image;

import com.jj.social.entity.Image;
import com.jj.social.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {

    private MultipartFile file;

    @NotNull
    private String caption;

    public Image toEntity(String postImgUrl, String imgName, User user) {
        return Image.builder()
                .oriImgName(file.getOriginalFilename())
                .imgName(imgName)
                .postImgUrl(postImgUrl)
                .caption(caption)
                .user(user)
                .build();
    }
}
