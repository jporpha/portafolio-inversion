package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.AssetDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import com.jp.orpha.portafolio_inversion.mappers.AssetMapper;
import com.jp.orpha.portafolio_inversion.repositories.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Override
    public List<AssetDto> getAllAssets() {
        return assetMapper.toDtoList(assetRepository.findAll());
    }

    @Override
    public AssetDto getAssetById(Long id) {
        AssetEntity asset = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return assetMapper.toDto(asset);
    }

    @Override
    public AssetDto createAsset(AssetDto assetDto) {
        AssetEntity entity = assetMapper.toEntity(assetDto);
        return assetMapper.toDto(assetRepository.save(entity));
    }

    @Override
    public AssetDto updateAsset(Long id, AssetDto assetDto) {
        AssetEntity existing = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));

        existing.setName(assetDto.getName());
        return assetMapper.toDto(assetRepository.save(existing));
    }

    @Override
    public Boolean deleteAssetById(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        assetRepository.deleteById(id);
        return true;
    }
}
