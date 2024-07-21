package com.palmerodev.storage_service_spr.config;

import com.palmerodev.storage_service_spr.controller.StorageController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Schedules {

    @Value("${app.storage-service.url}")
    String SERVER_URL;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Swagger URL: {}/swagger-ui/swagger-ui.html", SERVER_URL);
    }

}