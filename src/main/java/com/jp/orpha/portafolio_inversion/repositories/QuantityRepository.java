package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.QuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuantityRepository extends JpaRepository<QuantityEntity, Long> {

    List<QuantityEntity> findByDate(LocalDate date);
    List<QuantityEntity> findByPortfolioId(Long portfolioId);


}