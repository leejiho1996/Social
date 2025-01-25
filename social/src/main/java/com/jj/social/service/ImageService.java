package com.jj.social.service;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.ImageDto;
import com.jj.social.entity.Image;
import com.jj.social.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String filePath;

    public List<Image> getUserImages(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    @Transactional
    public void uploadImage(ImageDto ImageDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String originFilename = ImageDto.getFile().getOriginalFilename();
        String extension = originFilename.substring(originFilename.lastIndexOf("."));
        String imgName = uuid.toString() + "." + extension; // UUID화 된 이미지 이름

        String imgFilePath = filePath + "/" + imgName;

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
}
