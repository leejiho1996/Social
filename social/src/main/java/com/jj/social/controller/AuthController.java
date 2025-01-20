package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.SignupDto;
import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "auth/signin";
    }


    @GetMapping("/signup")
    public String register() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String register(@Valid SignupDto signupDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.error(error.getDefaultMessage());
            }

            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }

        User user = authService.registerUser(signupDto);
        log.info("registered user = {}" , user.toString());

        return "auth/signin";
    }

}
