package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.UserProfileDto;
import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomException;
import com.jj.social.handler.exception.CustomValidationApiException;
import com.jj.social.handler.exception.CustomValidationException;
import com.jj.social.repository.SubscribeRepository;
import com.jj.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscribeRepository subscribeRepository;

    @Transactional
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

    public UserProfileDto getUserProfileDto(Long pageUserId, PrincipalDetails principalDetails) {
        User user = userRepository.findById(pageUserId)
                .orElseThrow(() -> new CustomException("존재하지 않는 회원입니다."));
        Long requestUserId = principalDetails.getUser().getId();

        UserProfileDto userProfileDto = new UserProfileDto(user);
        userProfileDto.setImageCount(user.getImages().size());
        userProfileDto.setPageOwnerState(pageUserId.equals(requestUserId));
        userProfileDto.setSubscribeCount(subscribeRepository.countSubscribeBy(pageUserId)); // 구독자 수
        userProfileDto.setSubscribeState(subscribeRepository.getSubScribeState(requestUserId, pageUserId) == 1); // 현재 요청한 사람과 구독여부

        return userProfileDto;
    }
}
