package com.palmerodev.storage_service_spr.service;

import com.palmerodev.storage_service_spr.model.entity.ImageDataEntity;
import com.palmerodev.storage_service_spr.model.request.UploadImageRequest;
import com.palmerodev.storage_service_spr.model.response.DownloadImageResponse;
import com.palmerodev.storage_service_spr.repository.ImageDataEntityRepository;
import com.palmerodev.storage_service_spr.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StorageServiceImpl implements StorageService {

    private final ImageDataEntityRepository imageDataEntityRepository;
    private final ImageUtils imageUtils;

    @Override
    public String uploadImage(UploadImageRequest image) throws IOException {
        return imageDataEntityRepository.save(ImageDataEntity.builder()
                                                             .name(image.getName())
                                                             .type(image.getType())
                                                             .data(imageUtils.compressImage(image.getImage()))
                                                             .build()).getName();
    }

    @Override
    public DownloadImageResponse downloadImage(String name) {
        var imageDataEntity = imageDataEntityRepository.findByName(name);
        return imageDataEntity.map(img -> DownloadImageResponse.builder()
                                                               .name(img.getName())
                                                               .type(img.getType())
                                                               .image(imageUtils.decompressImage(img.getData()))
                                                               .build()).orElse(null);
    }

}
