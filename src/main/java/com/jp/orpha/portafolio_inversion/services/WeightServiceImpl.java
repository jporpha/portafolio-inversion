package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.WeightDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.WeightEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.mappers.WeightMapper;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.repositories.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightServiceImpl implements WeightService {

    private final WeightRepository weightRepository;
    private final WeightMapper weightMapper;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public List<WeightDto> getAllWeights() {
        return weightRepository.findAll()
                .stream()
                .map(weightMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WeightDto getWeightById(Long id) {
        WeightEntity entity = weightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return weightMapper.toDto(entity);
    }

    @Override
    public WeightDto createWeight(WeightDto weightDto) {
        WeightEntity entity = weightMapper.toEntity(weightDto);
        return weightMapper.toDto(weightRepository.save(entity));
    }

    @Override
    public WeightDto updateWeight(Long id, WeightDto weightDto) {
        WeightEntity existing = weightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));

        AssetEntity asset = assetRepository.findById(weightDto.getAssetId())
                .orElseThrow(() -> new NotFoundException("Asset", "id", weightDto.getAssetId()));

        PortfolioEntity portfolio = portfolioRepository.findById(weightDto.getPortfolioId())
                .orElseThrow(() -> new NotFoundException("Portfolio", "id", weightDto.getPortfolioId()));

        existing.setAsset(asset);
        existing.setPortfolio(portfolio);
        existing.setWeight(weightDto.getWeight());
        existing.setDate(weightDto.getDate());

        return weightMapper.toDto(weightRepository.save(existing));
    }

    @Override
    public Boolean deleteWeight(Long id) {
        if (!weightRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        weightRepository.deleteById(id);
        return true;
    }
}