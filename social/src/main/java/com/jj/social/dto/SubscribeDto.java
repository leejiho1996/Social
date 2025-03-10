package com.jj.social.dto;

import lombok.Data;

@Data
public class SubscribeDto {

    private Long id; // 해당 유저의 아이디

    private String nickname;

    private String profileImageUri;

    // 로그인한 유저가 모달에서 확인한 유저를 구독했는지 확인
    private boolean subscribeState;

    // 로그인한 유저가 모달에서 확인한 유저가 본인인지 확인
    private boolean equalUserState;
}
