package com.jp.orpha.portafolio_inversion.dtos;

import com.jp.orpha.portafolio_inversion.enums.RebalanceActionType;
import lombok.Data;

@Data
public class RebalanceActionDto {
    private Long assetId;
    private String assetName;
    private Double currentValue;
    private Double targetWeight;
    private Double targetValue;
    private Double difference;
    private Double quantity;
    private RebalanceActionType action;
}

