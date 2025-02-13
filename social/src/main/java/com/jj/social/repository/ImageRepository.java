package com.jj.social.repository;

import com.jj.social.dto.image.PopularImageDto;
import com.jj.social.dto.image.ProfileImageDto;
import com.jj.social.entity.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findByUserId(Long userId);

    // 메인 페이지 접속시 보여질 이미지 정보
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


    // 인기 게시글 페이지에 보여질 이미지 정보
    @Query(value = "select i.imgName, c.likeCount, i.userId  from Image i " +
            "inner join (select imageId, count(imageId) likeCount from Likes group by imageId) c " +
            "on i.id = c.imageId " +
            "order by likeCount desc",
             nativeQuery = true)
    List<PopularImageDto> findPopularImages();


    // 프로필 페이지에서 보여질 이미지 정보
    @Query(value = "select i.id, i.imgName, " +
            "(select count(*) from Likes where imageId = i.id) as likeCount " +
            "from Image i inner join User u on i.userId = u.id " +
            "where i.userId = :pageUserId",
             nativeQuery = true)
    List<ProfileImageDto> findProfileImageDtos(@Param("pageUserId") long pageUserId);
}
