package com.jj.social.controller.api;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.CMRespDto;
import com.jj.social.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesApiController {
    private final LikesService likesService;


    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> like(@PathVariable Long imageId,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {

        likesService.like(principalDetails.getUser().getId(), imageId);
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLike(@PathVariable Long imageId,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {

        likesService.unlike(principalDetails.getUser().getId(), imageId);
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }
}
