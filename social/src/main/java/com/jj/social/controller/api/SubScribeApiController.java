package com.jj.social.controller.api;

import com.jj.social.auth.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubScribeApiController {
    @PostMapping("/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@PathVariable String toUserId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return null;
    }

    @PostMapping("/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@PathVariable String toUserId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return null;
    }
}
