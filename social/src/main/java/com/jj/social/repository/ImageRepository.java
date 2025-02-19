package com.jj.social.repository;

import com.jj.social.dto.image.ImageMainDto;
import com.jj.social.dto.image.PopularImageDto;
import com.jj.social.dto.image.ProfileImageDto;
import com.jj.social.entity.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findByUserId(Long userId);



    // 메인 페이지 접속시 보여질 이미지 정보
    @Query(
    "select i, u, COUNT(l.id), " +
           "case when COUNT(myLike.id) > 0 then true else false end " +
    "from Image i "+
    "join fetch i.user u " +
    "left join Likes l on l.image = i " +
    "left join Likes myLike on myLike.image = i and myLike.user.id = :userId " +
    "where u.id in ( " +
        "select s.toUser.id from Subscribe s where s.fromUser.id = :userId " +
    ") " +
    "group by i, u "
    )
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

//
//    @EntityGraph(attributePaths = {"comments", "comments.user", "likes"})
//    @Query("select i " +
//            "from Image i " +
//            "join Subscribe s on i.user.id = s.toUser.id " +
//            "where s.fromUser.id = :userId"
//    )
//    List<ImageMainDto> findImageMainDto(@Param("userId") Long userId, Pageable pageable);
}
