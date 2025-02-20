package com.jj.social.service.oauth2;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.entity.User;
import com.jj.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class OAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        for (String s : userInfo.keySet()) {
            System.out.println(userInfo.get(s));
        }

        String username = "facebook__" + userInfo.get("id");
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User findedUser = userRepository.findByUsername(username);

        if (findedUser == null) {
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .nickname(name)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), userInfo);
        } else {
            return new PrincipalDetails(findedUser, oAuth2User.getAttributes());
        }
    }
}
