package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

@Data
public class AssetStatusDto {

    private Long assetId;
    private String assetName;
    private Double quantity;
    private Double price;
    private Double value;
    private Double weight;
}
