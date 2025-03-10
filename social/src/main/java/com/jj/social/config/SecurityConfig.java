package com.jj.social.config;

import com.jj.social.service.oauth2.OAuth2DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuth2DetailService oAuth2DetailService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(loginCustomizer -> loginCustomizer
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/")
                .usernameParameter("username"));

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/layout/**").permitAll()
                                // JSP경로 (/WEB-INF/view/**) 추가
                                .requestMatchers("/WEB-INF/views/**", "/auth/**","/error").permitAll()
                        .anyRequest().authenticated()
                        );

        // userInfoEndpoint -> 소셜 로그인 후 사용자 정보를 가져올 떄 설정 담당
        http.httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth2  -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2DetailService))
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public SecurityConfig(@Lazy OAuth2DetailService oAuth2DetailService) {
        this.oAuth2DetailService = oAuth2DetailService;
    }

    /**
     * SpringSecurity 6.3 부터 도입
     * 안전한 비밀번호인지 체크
     */
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
