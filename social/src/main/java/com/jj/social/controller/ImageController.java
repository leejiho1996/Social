package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.image.ImageDto;
import com.jj.social.dto.image.PopularImageDto;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.repository.UserRepository;
import com.jj.social.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }


    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<PopularImageDto> popularImageDtos = imageService.loadPopularImage();
        model.addAttribute("imageDto", popularImageDtos);
        return "image/popular";
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
