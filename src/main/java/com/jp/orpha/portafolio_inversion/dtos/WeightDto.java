package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeightDto {
    private Long id;
    private Long assetId;
    private Long portfolioId;
    private Double weight;
    private LocalDate date;
}

