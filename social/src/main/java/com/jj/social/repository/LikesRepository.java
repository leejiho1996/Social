package com.jj.social.repository;


import com.jj.social.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query(value = "insert into Likes(imageId, userId, createDate) values(:imageId, :userId, now())",
            nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void like(@Param("userId") Long userId, @Param("imageId") Long imageId);

    @Query(value = "delete from Likes where imageId = :imageId and userId = :userId",
            nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void unlike(@Param("userId") Long userId, @Param("imageId") Long imageId);

    @Query(value = "select like.image.id from Likes like where like.user.id = :userId")
    HashSet<Long> findUserId(long userId);

    @Query(value = "select post_id, count(*) as like_count " +
            "from likes " +
            "where post_id in (:postIds) " +
            "group by post_id",
            nativeQuery = true)
    List<Object[]> findLikeCount(@Param("postIds") List<Long> postIds);
}
