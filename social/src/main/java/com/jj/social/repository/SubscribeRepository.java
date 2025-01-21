package com.jj.social.repository;

import com.jj.social.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Query(value = "insert into Subscribe (fromUserId, toUserId, crateDate) values(:fromUserId, :toUserId, :now())",
            nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void doSubscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    @Query(value = "delete from Subscribe where fromUserId = :fromUserId AND toUserId = :toUserId",
            nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void doUnSubscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);
}

