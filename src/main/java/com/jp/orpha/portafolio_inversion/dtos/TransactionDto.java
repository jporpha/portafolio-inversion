package com.jp.orpha.portafolio_inversion.dtos;

import com.jp.orpha.portafolio_inversion.enums.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDto {

    private Long portfolioId;
    private Long assetId;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
}
