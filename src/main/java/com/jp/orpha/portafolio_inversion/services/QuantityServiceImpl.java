package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.QuantityDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.QuantityEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.mappers.QuantityMapper;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.repositories.QuantityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuantityServiceImpl implements QuantityService {

    private final QuantityRepository quantityRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;
    private final QuantityMapper quantityMapper;

    @Override
    public List<QuantityDto> getAllQuantities() {
        return quantityRepository.findAll()
                .stream()
                .map(quantityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuantityDto getQuantityById(Long id) {
        QuantityEntity entity = quantityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return quantityMapper.toDto(entity);
    }

    @Override
    @Transactional
    public QuantityDto createQuantity(QuantityDto quantityDto) {
        AssetEntity asset = assetRepository.findById(quantityDto.getAssetId())
                .orElseThrow(() -> new NotFoundException(this, "assetId", quantityDto.getAssetId()));
        PortfolioEntity portfolio = portfolioRepository.findById(quantityDto.getPortfolioId())
                .orElseThrow(() -> new NotFoundException(this, "portfolioId", quantityDto.getPortfolioId()));

        QuantityEntity quantity = new QuantityEntity();
        quantity.setAsset(asset);
        quantity.setPortfolio(portfolio);
        quantity.setDate(quantityDto.getDate());
        quantity.setQuantity(quantityDto.getQuantity());

        return quantityMapper.toDto(quantityRepository.save(quantity));
    }

    @Override
    public QuantityDto updateQuantity(Long id, QuantityDto quantityDto) {
        QuantityEntity existing = quantityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));

        AssetEntity asset = assetRepository.findById(quantityDto.getAssetId())
                .orElseThrow(() -> new NotFoundException(this, "assetId", quantityDto.getAssetId()));
        PortfolioEntity portfolio = portfolioRepository.findById(quantityDto.getPortfolioId())
                .orElseThrow(() -> new NotFoundException(this, "portfolioId", quantityDto.getPortfolioId()));

        existing.setAsset(asset);
        existing.setPortfolio(portfolio);
        existing.setDate(quantityDto.getDate());
        existing.setQuantity(quantityDto.getQuantity());

        return quantityMapper.toDto(quantityRepository.save(existing));
    }


    @Override
    public Boolean deleteQuantityById(Long id) {
        if (!quantityRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        quantityRepository.deleteById(id);
        return true;
    }
}
