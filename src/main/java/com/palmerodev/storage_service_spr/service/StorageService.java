package com.palmerodev.storage_service_spr.service;

import com.palmerodev.storage_service_spr.model.request.UploadImageRequest;
import com.palmerodev.storage_service_spr.model.response.DownloadImageResponse;

import java.io.IOException;

public interface StorageService {

    String uploadImage(UploadImageRequest image) throws IOException;

    DownloadImageResponse downloadImage(String name);

}
