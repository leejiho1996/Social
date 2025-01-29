package com.jj.social.service;

import com.jj.social.handler.exception.CustomException;
import com.jj.social.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void like(Long principalId, Long imageId) {
        try {
            likesRepository.like(principalId, imageId);
        } catch (Exception e) {
            log.info("likes Error : {}", e.getMessage());
            log.info("principal id : {}", principalId);
            log.info("image id : {}", imageId);
            throw new CustomException("이미 좋아요를 하셨습니다.");
        }

    }

    @Transactional
    public void unlike(Long principalId, Long imageId) {
        likesRepository.unlike(principalId, imageId);
    }
}
