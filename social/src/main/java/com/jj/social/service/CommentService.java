package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.comment.CommentDto;
import com.jj.social.entity.Comment;
import com.jj.social.entity.Image;
import com.jj.social.handler.exception.CustomApiException;
import com.jj.social.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment writeComment(CommentDto commentDto, PrincipalDetails principalDetails) {
        String content = commentDto.getContent();
        Long imageId = commentDto.getImageId();

        // 아이디만 가지는 껍데기 Image 생성
        Image image = Image.builder()
                .id(imageId)
                .build();

        Comment newComment = Comment.builder()
                .content(content)
                .user(principalDetails.getUser())
                .image(image)
                .build();

        return commentRepository.save(newComment);
    }


    @Transactional
    public void deleteComment(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
