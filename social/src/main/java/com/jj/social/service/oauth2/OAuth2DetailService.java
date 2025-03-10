package com.jj.social.service.oauth2;

import com.jj.social.auth.FacebookInfo;
import com.jj.social.auth.GoogleInfo;
import com.jj.social.auth.OAuth2UserInfo;
import com.jj.social.auth.PrincipalDetails;
import com.jj.social.entity.User;
import com.jj.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getClientName().equals("Google")) {
            log.info("구글 로그인 요청: {}", userRequest.getClientRegistration().getClientName());
            oAuth2UserInfo = new GoogleInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
            log.info("페이스북 로그인 요청: {}", userRequest.getClientRegistration().getClientName());
            oAuth2UserInfo = new FacebookInfo(oAuth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getClientName().equals("Naver")) {
//            oAuth2UserInfo = new NaverInfo(oauth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getClientName().equals("Kakao")) {
//            oAuth2UserInfo = new KakaoInfo(oauth2User.getAttributes());
    }

        String username = oAuth2UserInfo.getUsername();
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User findedUser = userRepository.findByUsername(username);

        if (findedUser == null) {
            User user = User.builder()
                    .username(username)
                    .role("ROLE_USER")
                    .email(email)
                    .nickname(name)
                    .password(password)
                    .build();

            findedUser = userRepository.save(user);
            return new PrincipalDetails(findedUser, oAuth2UserInfo.getAttributes());
        } else {
            return new PrincipalDetails(findedUser, oAuth2UserInfo.getAttributes());
        }
    }
}
