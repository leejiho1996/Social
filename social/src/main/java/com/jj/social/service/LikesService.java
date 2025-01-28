package com.jj.social.service;

import com.jj.social.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    public void like() {

    }

    public void unlike() {}
}
