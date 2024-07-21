package com.palmerodev.storage_service_spr.controller;

import com.palmerodev.storage_service_spr.model.request.UploadImageRequest;
import com.palmerodev.storage_service_spr.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @GetMapping("/{name}")
    public ResponseEntity<?> download(@PathVariable String name) {
        var imageData = storageService.downloadImage(name);
        if (Objects.isNull(imageData)) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/" + imageData.getType())).body(imageData.getImage());
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestBody UploadImageRequest file) {
        String imageName;
        try {
            imageName = storageService.uploadImage(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.created(URI.create("/storage/" + imageName)).build();
    }

}
