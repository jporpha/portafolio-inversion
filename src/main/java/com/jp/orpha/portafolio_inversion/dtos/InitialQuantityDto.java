package com.jp.orpha.portafolio_inversion.dtos;

import lombok.Data;

@Data
public class InitialQuantityDto {
    private Long assetId;
    private String assetName;
    private Double weight;
    private Double price;
    private Double quantity;
}
