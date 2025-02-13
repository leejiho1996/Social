package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.image.ProfileImageDto;
import com.jj.social.dto.user.UserProfileDto;
import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomApiException;
import com.jj.social.handler.exception.CustomException;
import com.jj.social.handler.exception.CustomValidationApiException;
import com.jj.social.repository.ImageRepository;
import com.jj.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String filePath;

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
        Long requestUserId = principalDetails.getUser().getId();
        UserProfileDto userProfileDto = userRepository.findUserProfile(pageUserId, requestUserId)
                .orElseThrow(() -> new CustomException("존재하지 않는 회원입니다."));

        List<ProfileImageDto> profileImageDtos = imageRepository.findProfileImageDtos(pageUserId);

        userProfileDto.setPageOwnerState(pageUserId.equals(requestUserId));
        userProfileDto.setImageList(profileImageDtos);
        userProfileDto.setImageCount(profileImageDtos.size());

//        userProfileDto.setSubscribeCount(subscribeRepository.countSubscribeBy(pageUserId)); // 구독자 수
//        userProfileDto.setSubscribeState(subscribeRepository.getSubScribeState(requestUserId, pageUserId) == 1); // 현재 요청한 사람과 구독여부

        return userProfileDto;
    }

    @Transactional
    public User changeProfileImage(Long principalId, Long pageUserId, MultipartFile profileImage) {
        if (!principalId.equals(pageUserId)) {
            throw new CustomValidationApiException("허가되지 않은 사용자입니다.");
        }

        UUID uuid = UUID.randomUUID();
        String originalFilename = profileImage.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String imgName = uuid + extension;

        String imgFilePath = filePath + imgName;

        try { // 파일 저장
            FileOutputStream fos = new FileOutputStream(imgFilePath);
            fos.write(profileImage.getBytes());
            fos.close();
        } catch (Exception e) {
            e.getMessage();
        }

        User user = userRepository.findById(principalId).orElseThrow(()->
            new CustomApiException("존재하지 않는 유저입니다.")
        );

        user.setProfileImageUri(imgName);

        return user;
    }
}
