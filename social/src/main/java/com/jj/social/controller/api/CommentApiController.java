package com.jj.social.controller.api;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.CMRespDto;
import com.jj.social.dto.comment.CommentDto;
import com.jj.social.entity.Comment;
import com.jj.social.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("댓글쓰기로직 성공 = {}", commentDto.toString());
        Comment comment = commentService.writeComment(commentDto, principalDetails);

        return new ResponseEntity<>(
                new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.OK
        );
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(
                new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK
        );
    }
}
