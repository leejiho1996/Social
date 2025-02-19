package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.comment.CommentStoryDto;
import com.jj.social.dto.image.ImageDto;
import com.jj.social.dto.image.ImageStoryDto;
import com.jj.social.dto.image.PopularImageDto;
import com.jj.social.entity.*;
import com.jj.social.repository.CommentRepository;
import com.jj.social.repository.ImageRepository;
import com.jj.social.repository.LikesRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;
    private final EntityManager em;
    private final CommentRepository commentRepository;

    @Value("${file.path}")
    private String filePath;

    QImage image = QImage.image;
    QSubscribe subscribe = QSubscribe.subscribe;
    QUser user = QUser.user;

    public List<Image> getUserImages(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    @Transactional
    public void uploadImage(ImageDto ImageDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String originFilename = ImageDto.getFile().getOriginalFilename();
        String extension = originFilename.substring(originFilename.lastIndexOf("."));
        String imgName = uuid + extension; // UUID화 된 이미지 이름

        String imgFilePath = filePath + imgName;

        try {
            FileOutputStream fos = new FileOutputStream(imgFilePath);
            fos.write(ImageDto.getFile().getBytes());
            fos.close();
        } catch (Exception e) {
            e.getMessage();
        }

        Image image = ImageDto.toEntity(imgFilePath, imgName, principalDetails.getUser());
        imageRepository.save(image);
    }

    /**
     * 메인페이지 접속시 나오는 게시글 정보 load
     */
    public List<ImageStoryDto> loadImageStory(Long principalId, Pageable pageable) {
        List<Object[]> results = imageRepository.findImagesWithLikesAndSubscriptions(principalId, pageable);

        List<ImageStoryDto> imageStoryDtos =  results.stream().map(result -> {
            Image image = (Image) result[0];
            User user = (User) result[1];
            return new ImageStoryDto(
                    image,
                    user,
                    ((Number) result[2]).intValue(),
                    (Boolean) result[3]);
        }
        ).toList();

        // imageId를 뽑아 리스트를 만든다
        List<Long> imageIds = imageStoryDtos.stream()
                .map(ImageStoryDto::getId)
                .toList();

        log.info("imageIds: {}", imageIds);

        // imageId의 댓글 목록을 가져온다
        List<CommentStoryDto> comments = commentRepository.findAllByImageList(imageIds);
        log.info("comments = {}", comments);

        // 해당 댓글들을 Map을 이용해 이미지별 댓글들로 매핑한다
        Map<Long, List<CommentStoryDto>> commentMap = comments.stream()
                .collect(Collectors.groupingBy(CommentStoryDto::getImageId));
        log.info("commentMap = {}", commentMap);

        for (ImageStoryDto imageStoryDto : imageStoryDtos) {
            if (commentMap.containsKey(imageStoryDto.getId())) {
                imageStoryDto.setCommentList(commentMap.get(imageStoryDto.getId()));
            } else {
                imageStoryDto.setCommentList(new ArrayList<CommentStoryDto>());
            }
        }

        return imageStoryDtos;

    }

    public List<PopularImageDto> loadPopularImage() {
         return imageRepository.findPopularImages();
    }



    /**
     * ImageStoryDto queryDsl style
     */

//    public List<ImageStoryDto> loadImageStory(Long principalId, Pageable pageable) {
//        JPAQuery<ImageStoryDto> query = new JPAQuery<>(em);
//
//        List<ImageStoryDto> images = query.select(
//                        Projections.bean(ImageStoryDto.class,
//                                image.id, user.id.as("userId"), user.profileImageUri,
//                                user.username,image.caption, image.oriImgName,image.imgName))
//                .from(image).innerJoin(user).on(image.user.id.eq(user.id))
//                .where(image.user.id.in(
//                        JPAExpressions.select(subscribe.toUser.id)
//                                .from(subscribe)
//                                .where(subscribe.fromUser.id.eq(principalId))
//                ))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        HashSet<Long> likesSet = likesRepository.findUserId(principalId); // 현재 유저가 좋아요 한 이미지 id search
//
//        likesRepository.findLikeCount();
//        images.stream().forEach(img -> {
//            if (likesSet.contains(img.getId())) {
//                img.setLikeState(true);
//            }
//        });
//
//        return images;
//    }
}
