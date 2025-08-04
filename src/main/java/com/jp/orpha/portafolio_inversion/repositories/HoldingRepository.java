package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.HoldingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingRepository extends JpaRepository<HoldingEntity, Long> {

    List<HoldingEntity> findByPortfolioId(Long portfolioId);
    void deleteByPortfolioId(Long portfolioId);

}