package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.QuantityDto;

import java.util.List;

public interface QuantityService {
    List<QuantityDto> getAllQuantities();
    QuantityDto getQuantityById(Long id);
    QuantityDto createQuantity(QuantityDto quantityDto);
    QuantityDto updateQuantity(Long id, QuantityDto quantityDto);
    Boolean deleteQuantityById(Long id);
}