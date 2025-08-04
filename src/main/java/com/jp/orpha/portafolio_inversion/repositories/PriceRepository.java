package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByDate(LocalDate date);
    Optional<PriceEntity> findTopByAssetIdOrderByDateDesc(Long assetId);
    List<PriceEntity> findByAssetIdIn(Set<Long> assetIds);
    Optional<PriceEntity> findByAssetIdAndDate(Long assetId, LocalDate date);
    List<PriceEntity>  findByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<PriceEntity> findTopByAssetIdAndDateLessThanEqualOrderByDateDesc(Long assetId, LocalDate date);





}