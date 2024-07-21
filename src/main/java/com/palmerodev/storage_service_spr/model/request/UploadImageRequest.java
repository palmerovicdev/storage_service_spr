package com.palmerodev.storage_service_spr.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageRequest {
    private byte[] image;
    private String name;
    private String type;

}
