package com.jj.social.controller.api;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.CMRespDto;
import com.jj.social.dto.SubscribeDto;
import com.jj.social.dto.user.UserUpdateDto;
import com.jj.social.entity.User;
import com.jj.social.handler.exception.CustomValidationApiException;
import com.jj.social.service.SubscribeService;
import com.jj.social.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/user/{id}")
    public CMRespDto<?> update(@PathVariable Long id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User userEntity = userService.modifyUser(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }

    @GetMapping("/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Long pageUserId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(
                new CMRespDto<>(1, "구독정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/user/{pageUserId}/profileImageUrl")
    public ResponseEntity<?> changeProfileImage(@PathVariable Long pageUserId,
                                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                // MultipartFile로 받을 때, 변수명을 해당 form태그의 name명과 일치시켜야 한다
                                                MultipartFile profileImageFile ) {

        User user = userService.changeProfileImage(principalDetails.getUser().getId(), pageUserId, profileImageFile);
        principalDetails.setUser(user);

        return new ResponseEntity<>(
                new CMRespDto<>(1, "프로필사진 변경성공", null), HttpStatus.OK
        );

    }
}
