package com.palmerodev.storage_service_spr;

import com.palmerodev.storage_service_spr.model.request.UploadImageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StorageServiceSprApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldUploadImage() throws IOException {
        File imageFile = new File("src/test/resources/img.jpeg");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        var name = new Random().nextLong() + imageFile.getName();

        UploadImageRequest imageRequest = UploadImageRequest.builder()
                                                            .image(imageBytes)
                                                            .name(name)
                                                            .type(imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1)) // Ajusta el tipo de archivo según corresponda
                                                            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UploadImageRequest> request = new HttpEntity<>(imageRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/storage",
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        System.out.println(response.getHeaders().getLocation());
    }

    @Test
    void shouldDownloadImage() throws IOException {
        File imageFile = new File("src/test/resources/img.jpeg");
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        var name = new Random().nextLong() + imageFile.getName();

        UploadImageRequest imageRequest = UploadImageRequest.builder()
                                                            .image(imageBytes)
                                                            .name(name)
                                                            .type(imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1)) // Ajusta el tipo de archivo según corresponda
                                                            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UploadImageRequest> request = new HttpEntity<>(imageRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/storage",
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        System.out.println(response.getHeaders().getLocation());

        HttpHeaders downloadHeaders = new HttpHeaders();
        downloadHeaders.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));

        var downloadRequest = new HttpEntity<>(downloadHeaders);

        ResponseEntity<byte[]> downloadResponse = restTemplate.exchange(
                Objects.requireNonNull(response.getHeaders().getLocation()).toString(),
                HttpMethod.GET,
                downloadRequest,
                byte[].class);

        assertThat(downloadResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(downloadResponse.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(downloadResponse.getBody()).isEqualTo(imageBytes);
    }

}
