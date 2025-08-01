package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceDto {
    private Long id;
    private Long assetId;
    private LocalDate date;
    private Double value;
}
