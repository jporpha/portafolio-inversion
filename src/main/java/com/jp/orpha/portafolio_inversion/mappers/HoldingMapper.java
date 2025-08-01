package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.HoldingDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.HoldingEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HoldingMapper {

    HoldingMapper INSTANCE = Mappers.getMapper(HoldingMapper.class);

    @Mapping(source = "portfolio.id", target = "portfolioId")
    @Mapping(source = "asset.id", target = "assetId")
    HoldingDto toDto(HoldingEntity entity);

    @Mapping(source = "portfolioId", target = "portfolio", qualifiedByName = "mapPortfolioIdToEntity")
    @Mapping(source = "assetId", target = "asset", qualifiedByName = "mapAssetIdToEntity")
    HoldingEntity toEntity(HoldingDto dto);

    @Named("mapPortfolioIdToEntity")
    default PortfolioEntity mapPortfolioIdToEntity(Long id) {
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setId(id);
        return portfolio;
    }

    @Named("mapAssetIdToEntity")
    default AssetEntity mapAssetIdToEntity(Long id) {
        AssetEntity asset = new AssetEntity();
        asset.setId(id);
        return asset;
    }
}
