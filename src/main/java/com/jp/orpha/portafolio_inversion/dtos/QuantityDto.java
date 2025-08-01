package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantityDto {
    private Long id;
    private Long assetId;
    private Long portfolioId;
    private LocalDate date;
    private Double quantity;
}

