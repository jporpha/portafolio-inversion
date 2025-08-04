package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioStatusDto {

    private Long portfolioId;
    private String portfolioName;
    private List<AssetStatusDto> assets;
    private Double totalValue;
}
