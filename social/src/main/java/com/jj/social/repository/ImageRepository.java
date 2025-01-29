package com.jj.social.repository;

import com.jj.social.entity.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findByUserId(Long userId);

    @Query("""
    SELECT i, u, COUNT(l.id),
           CASE WHEN COUNT(myLike.id) > 0 THEN true ELSE false END
    FROM Image i
    JOIN FETCH i.user u
    LEFT JOIN Likes l ON l.image = i
    LEFT JOIN Likes myLike ON myLike.image = i AND myLike.user.id = :userId
    WHERE u.id IN (
        SELECT s.toUser.id FROM Subscribe s WHERE s.fromUser.id = :userId
    )
    GROUP BY i, u
    """)
    List<Object[]> findImagesWithLikesAndSubscriptions(@Param("userId") Long userId, Pageable pageable);
}
