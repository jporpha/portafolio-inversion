package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.PriceDto;

import java.util.List;

public interface PriceService {
    List<PriceDto> getAllPrices();
    PriceDto getPriceById(Long id);
    PriceDto createPrice(PriceDto priceDto);
    PriceDto updatePrice(Long id, PriceDto priceDto);
    Boolean deletePriceById(Long id);
}
