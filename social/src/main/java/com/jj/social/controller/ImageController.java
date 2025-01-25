package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.ImageDto;
import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.repository.ImageRepository;
import com.jj.social.repository.UserRepository;
import com.jj.social.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageDto imageDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        imageService.uploadImage(imageDto, principalDetails);

        if (imageDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        return "redirect:/user/" + principalDetails.getUser().getId();
    }

}
