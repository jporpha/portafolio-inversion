package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
    Optional<AssetEntity> findByName(String name);
}