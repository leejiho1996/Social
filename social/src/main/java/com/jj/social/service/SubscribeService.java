package com.jj.social.service;

import com.jj.social.handler.exception.CustomApiException;
import com.jj.social.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public void subScribe(Long fromUserId, Long toUserId) {
        try {
            subscribeRepository.doSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            log.info("exception message : {}", e.getMessage());
            throw new CustomApiException("이미 구독하셨습니다.");
        }

    }

    public void unSubScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.doUnSubscribe(fromUserId, toUserId);
    }
}
