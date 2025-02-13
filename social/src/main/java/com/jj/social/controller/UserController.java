package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.user.UserProfileDto;
import com.jj.social.service.ImageService;
import com.jj.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model,
                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto userProfile = userService.getUserProfileDto(id, principalDetails);

        model.addAttribute("userDto", userProfile);
        model.addAttribute("images", userProfile.getImageList());
        return "user/profile";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model) {
        log.info("세큐리티 정보 확인 : {}", principalDetails.getUser());
//        model.addAttribute("principal", principalDetails.getUser());
        return "user/update";
    }
}
