package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.PriceDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.mappers.PriceMapper;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import com.jp.orpha.portafolio_inversion.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final AssetRepository assetRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<PriceDto> getAllPrices() {
        return priceRepository.findAll().stream()
                .map(priceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDto getPriceById(Long id) {
        PriceEntity entity = priceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return priceMapper.toDto(entity);
    }

    @Override
    public PriceDto createPrice(PriceDto dto) {
        AssetEntity asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new NotFoundException("Asset", "id", dto.getAssetId()));
        PriceEntity entity = priceMapper.toEntity(dto, asset);
        return priceMapper.toDto(priceRepository.save(entity));
    }

    @Override
    public PriceDto updatePrice(Long id, PriceDto dto) {
        PriceEntity existing = priceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        AssetEntity asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new NotFoundException("Asset", "id", dto.getAssetId()));

        priceMapper.updateEntityFromDto(dto, existing, asset);
        return priceMapper.toDto(priceRepository.save(existing));
    }

    @Override
    public Boolean deletePriceById(Long id) {
        if (!priceRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        priceRepository.deleteById(id);
        return true;
    }
}