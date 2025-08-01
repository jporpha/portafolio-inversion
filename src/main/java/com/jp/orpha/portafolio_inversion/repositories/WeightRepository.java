package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.WeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeightRepository extends JpaRepository<WeightEntity, Long> {
    List<WeightEntity> findByDate(LocalDate date);
    List<WeightEntity> findByPortfolioId(Long portfolioId);
}