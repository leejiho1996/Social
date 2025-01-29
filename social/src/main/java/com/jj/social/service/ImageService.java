package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.ImageDto;
import com.jj.social.dto.ImageStoryDto;
import com.jj.social.entity.*;
import com.jj.social.repository.ImageRepository;
import com.jj.social.repository.LikesRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;
    private final EntityManager em;

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
        String imgName = uuid.toString() + extension; // UUID화 된 이미지 이름

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

    public List<ImageStoryDto> loadImageStory(Long principalId, Pageable pageable) {
        List<Object[]> results = imageRepository.findImagesWithLikesAndSubscriptions(principalId, pageable);

        return results.stream().map(result -> new ImageStoryDto(
                ((Image) result[0]).getId(),
                ((Image) result[0]).getCaption(),
                ((Image) result[0]).getOriImgName(),
                ((Image) result[0]).getImgName(),
                ((User) result[1]).getId(),
                ((User) result[1]).getUsername(),
                ((User) result[1]).getProfileImageUri(),
                ((Number) result[2]).intValue(),
                (Boolean) result[3])
        ).toList();
    }

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
