package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.AssetDto;
import com.jp.orpha.portafolio_inversion.entities.AssetEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetDto toDto(AssetEntity entity);
    AssetEntity toEntity(AssetDto dto);
    List<AssetDto> toDtoList(List<AssetEntity> entities);
    List<AssetEntity> toEntityList(List<AssetDto> dtos);
}
