package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.QuantityDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.entities.QuantityEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuantityMapper {

    @Mapping(source = "asset.id", target = "assetId")
    @Mapping(source = "portfolio.id", target = "portfolioId")
    QuantityDto toDto(QuantityEntity entity);

    @Mapping(source = "assetId", target = "asset.id")
    @Mapping(source = "portfolioId", target = "portfolio.id")
    QuantityEntity toEntity(QuantityDto dto);

    @AfterMapping
    default void linkEntities(QuantityDto dto, @MappingTarget QuantityEntity entity) {
        if (dto.getAssetId() != null) {
            AssetEntity asset = new AssetEntity();
            asset.setId(dto.getAssetId());
            entity.setAsset(asset);
        }
        if (dto.getPortfolioId() != null) {
            PortfolioEntity portfolio = new PortfolioEntity();
            portfolio.setId(dto.getPortfolioId());
            entity.setPortfolio(portfolio);
        }
    }
}
