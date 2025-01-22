package com.jj.social.service;

import com.jj.social.dto.ImageDto;
import com.jj.social.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String filePath;

    public void uploadImage(ImageDto ImageDto) {
        UUID uuid = UUID.randomUUID();
        String originFilename = ImageDto.getFile().getOriginalFilename();
        String extension = originFilename.substring(originFilename.lastIndexOf("."));
        String imgName = uuid.toString() + "." + extension;

        String imgFilePath = filePath + "/" + imgName;

        try {
            FileOutputStream fos = new FileOutputStream(imgFilePath);
            fos.write(ImageDto.getFile().getBytes());
            fos.close();
        } catch (Exception e) {
            e.getMessage();
        }

//        imageRepository.save()
    }

}
