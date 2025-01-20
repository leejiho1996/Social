package com.jj.social.service;

import com.jj.social.dto.SignupDto;
import com.jj.social.entity.User;
import com.jj.social.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupDto signupDto) {

        Optional<User> byEmail = userRepository.findByEmail(signupDto.getEmail());
        if (byEmail.isPresent()) {
            throw new EntityExistsException();
        }

        // password 인코딩
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        log.info("Password Match : {}", passwordEncoder.matches("1234", signupDto.getPassword()));
        // role
        signupDto.setRole("ROLE_USER");

        return userRepository.save(signupDto.toEntity());
    }
}
