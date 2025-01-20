package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String profile() {
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
