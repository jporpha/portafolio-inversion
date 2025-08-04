package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PortfolioDailyStatusDto {
    private LocalDate date;
    private Double totalValue;
    private List<AssetWeightDto> assetsWeight;
}
