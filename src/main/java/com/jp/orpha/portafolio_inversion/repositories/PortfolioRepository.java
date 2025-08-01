package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {

}