package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;
import java.util.List;

@Data
public class RebalanceResultDto {
    private Long portfolioId;
    private String portfolioName;
    private Double totalValue;
    private List<RebalanceActionDto> actions;
}

