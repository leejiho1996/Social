package com.jj.social.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {

    private MultipartFile file;

    private String caption;
}
