package com.jj.social.service;

import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomValidationApiException;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User modifyUser(long id, User user) {

        User userEntity = userRepository.findById(id)
                .orElseThrow( () -> new CustomValidationApiException("찾츨 수 없는 아이디 입니다."));

        userEntity.setNickname(user.getNickname());
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }
}
