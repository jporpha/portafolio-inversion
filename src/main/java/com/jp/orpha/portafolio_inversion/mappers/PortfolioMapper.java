package com.jp.orpha.portafolio_inversion.mappers;

import com.jp.orpha.portafolio_inversion.dtos.PortfolioDto;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    PortfolioMapper INSTANCE = Mappers.getMapper(PortfolioMapper.class);

    PortfolioDto toDto(PortfolioEntity entity);
    PortfolioEntity toEntity(PortfolioDto dto);
}