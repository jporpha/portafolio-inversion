package com.jp.orpha.portafolio_inversion.repositories;

import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByDate(LocalDate date);

}