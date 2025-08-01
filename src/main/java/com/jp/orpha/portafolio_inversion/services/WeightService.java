package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.WeightDto;

import java.util.List;

public interface WeightService {
    List<WeightDto> getAllWeights();
    WeightDto getWeightById(Long id);
    WeightDto createWeight(WeightDto weightDto);
    WeightDto updateWeight(Long id, WeightDto weightDto);
    Boolean deleteWeight(Long id);
}
