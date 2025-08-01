package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.PriceDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import com.jp.orpha.portafolio_inversion.entities.PriceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(source = "asset.id", target = "assetId")
    PriceDto toDto(PriceEntity entity);

    @Mapping(target = "asset", ignore = true)
    PriceEntity toEntity(PriceDto dto, @Context AssetEntity asset);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "asset", ignore = true)
    void updateEntityFromDto(PriceDto dto, @MappingTarget PriceEntity entity, @Context AssetEntity asset);
}
