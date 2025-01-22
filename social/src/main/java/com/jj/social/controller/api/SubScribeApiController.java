package com.jj.social.controller.api;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.CMRespDto;
import com.jj.social.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubScribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@PathVariable Long toUserId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {

        long fromUserId = principalDetails.getUser().getId();
        subscribeService.subScribe(fromUserId, toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독완료", null), HttpStatus.OK);
    }

    @PostMapping("/unsubscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@PathVariable Long toUserId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        long fromUserId = principalDetails.getUser().getId();
        subscribeService.unSubScribe(fromUserId, toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독취소완료", null), HttpStatus.OK);
    }
}
