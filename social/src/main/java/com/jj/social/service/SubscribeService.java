package com.jj.social.service;

import com.jj.social.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public void subScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.doSubscribe(fromUserId, toUserId);
    }

    public void unSubScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.doUnSubscribe(fromUserId, toUserId);
    }
}
