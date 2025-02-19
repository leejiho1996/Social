package com.jj.social.repository;

import com.jj.social.dto.comment.CommentStoryDto;
import com.jj.social.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // return 값이 boolean이라 이미지에 대한 정보를 얻을 수 없음
    @Query(value = "Insert into coment(content, imageId, userId, createDate) values(:content, :imageId, :userId, now())",
    nativeQuery = true)
    Comment saveComment(@Param("content") String content, @Param("imageId") Long imageId, @Param("userId") Long userId);

    @Query("select new com.jj.social.dto.comment.CommentStoryDto(c.id, c.content, u.id, u.nickname, i.id) " +
            "from Comment c " +
            "join c.user u " +
            "join c.image i " +
            "where i.id in :imageList order by c.id")
    List<CommentStoryDto> findAllByImageList(@Param("imageList") List<Long> imageList);
}
