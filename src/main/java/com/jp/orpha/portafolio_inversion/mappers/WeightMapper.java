package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.WeightDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.WeightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeightMapper {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "portfolio.id", target = "portfolioId")
    WeightDto toDto(WeightEntity entity);

    @Mapping(source = "assetId", target = "asset.id")
    @Mapping(source = "portfolioId", target = "portfolio.id")
    WeightEntity toEntity(WeightDto dto);

    default AssetEntity mapAsset(Long id) {
        if (id == null) return null;
        AssetEntity asset = new AssetEntity();
        asset.setId(id);
        return asset;
    }

    default PortfolioEntity mapPortfolio(Long id) {
        if (id == null) return null;
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setId(id);
        return portfolio;
    }
}
