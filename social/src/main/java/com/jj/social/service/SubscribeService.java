package com.jj.social.service;

import com.jj.social.dto.SubscribeDto;
import com.jj.social.entity.QSubscribe;
import com.jj.social.entity.QUser;
import com.jj.social.handler.exception.CustomApiException;
import com.jj.social.repository.SubscribeRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    QUser user = QUser.user;
    QSubscribe subscribe = QSubscribe.subscribe;

    @Transactional
    public void subScribe(Long fromUserId, Long toUserId) {
        try {
            subscribeRepository.doSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            log.info("exception message : {}", e.getMessage());
            throw new CustomApiException("이미 구독하셨습니다.");
        }

    }

    @Transactional
    public void unSubScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.doUnSubscribe(fromUserId, toUserId);
    }

    public List<SubscribeDto> subscribeList(Long principalId, Long pageUserId) {
        JPAQuery<SubscribeDto> query = new JPAQuery<>(em);

        return query
                .select(Projections.bean(
                        SubscribeDto.class,
                        user.id,
                        user.username.as("nickname"), // nickname 은 DTO의 필드명
                        user.profileImageUri,
                        JPAExpressions.selectOne()
                                .from(subscribe)
                                .where(
                                        subscribe.fromUser.id.eq(principalId)
                                                .and(subscribe.toUser.id.eq(user.id))
                                )
                                .isNotNull().as("subscribeState"),
                        user.id.eq(principalId).as("equalUserState")
                ))
                .from(user)
                .innerJoin(subscribe)
                .on(user.id.eq(subscribe.toUser.id))
                .where(subscribe.fromUser.id.eq(pageUserId))
                .fetch();
    }

    public List<SubscribeDto> subscribeList2(Long principalId, Long pageUserId) {
        StringBuilder sb = new StringBuilder();

        sb.append("select u.id, u.name, u.profileImageUri, ");
        sb.append("if((select 1 from subscribe where fromUserId = ? and toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if((?=u.id), 1, 0) equalUserState ");
        sb.append("from User u inner join Subscribe s on u.id = s.toUserId where fromUserId = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos =  result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }
}
