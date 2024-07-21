package com.palmerodev.storage_service_spr.repository;

import com.palmerodev.storage_service_spr.model.entity.ImageDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataEntityRepository extends JpaRepository<ImageDataEntity, Long> {
    Optional<ImageDataEntity> findByName(String name);

}