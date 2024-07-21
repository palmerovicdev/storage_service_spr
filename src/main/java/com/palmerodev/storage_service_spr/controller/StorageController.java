package com.palmerodev.storage_service_spr.controller;

import com.palmerodev.storage_service_spr.model.request.UploadImageRequest;
import com.palmerodev.storage_service_spr.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Storage", description = "Storage controller")
public class StorageController {
    private final StorageService storageService;

    @Operation(summary = "Download an image", description = "Download an image from DB", responses = {
            @ApiResponse(responseCode = "200", description = "Image downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    }, method = "GET")
    @GetMapping("/{name}")
    public ResponseEntity<?> download(
            @Parameter(description = "Image name", required = true)
            @PathVariable String name) {
        var imageData = storageService.downloadImage(name);
        if (Objects.isNull(imageData)) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/" + imageData.getType())).body(imageData.getImage());
    }

    @Operation(summary = "Store a new image", description = "Store a new image in DB", responses = {
            @ApiResponse(responseCode = "201", description = "Image stored successfully"),
            @ApiResponse(responseCode = "500", description = "Error storing image")
    }, method = "POST")
    @PostMapping
    public ResponseEntity<?> upload(
            @Parameter(description = "Upload image request", required = true)
            @RequestBody UploadImageRequest file) {
        String imageName;
        try {
            imageName = storageService.uploadImage(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.created(URI.create("/storage/" + imageName)).build();
    }

}
