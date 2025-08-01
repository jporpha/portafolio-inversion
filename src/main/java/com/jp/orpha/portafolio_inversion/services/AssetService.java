package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.AssetDto;
import java.util.List;

public interface AssetService {
    List<AssetDto> getAllAssets();
    AssetDto getAssetById(Long id);
    AssetDto createAsset(AssetDto assetDto);
    AssetDto updateAsset(Long id, AssetDto assetDto);
    Boolean deleteAssetById(Long id);
}