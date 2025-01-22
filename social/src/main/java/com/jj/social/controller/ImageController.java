package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.ImageDto;
import com.jj.social.repository.ImageRepository;
import com.jj.social.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

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

        imageService.uploadImage(imageDto);

        return "redirect:/user/" + principalDetails.getUser().getId();
    }
}
