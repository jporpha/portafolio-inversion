package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PortfolioPerformanceDto {
    private LocalDate date;
    private Double totalValue;
}

