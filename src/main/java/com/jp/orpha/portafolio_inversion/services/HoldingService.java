package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.HoldingDto;

import java.util.List;

public interface HoldingService {
    List<HoldingDto> getAllHoldings();
    HoldingDto getHoldingById(Long id);
    HoldingDto createHolding(HoldingDto holdingDto);
    HoldingDto updateHolding(Long id, HoldingDto holdingDto);
    Boolean deleteHoldingById(Long id);
}
