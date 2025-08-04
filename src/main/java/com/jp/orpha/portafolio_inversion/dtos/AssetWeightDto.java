package com.jp.orpha.portafolio_inversion.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AssetWeightDto {
    private Long assetId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String assetName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double value;
    private Double weight;
}
