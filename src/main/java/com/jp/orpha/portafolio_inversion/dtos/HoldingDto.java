package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

@Data
public class HoldingDto {
    private Long id;
    private Long portfolioId;
    private Long assetId;
    private Double quantity;
}
