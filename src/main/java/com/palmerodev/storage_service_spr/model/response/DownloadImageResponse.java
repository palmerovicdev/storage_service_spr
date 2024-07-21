package com.palmerodev.storage_service_spr.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadImageResponse {
    private byte[] image;
    private String name;
    private String type;

}
