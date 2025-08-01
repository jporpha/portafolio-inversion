package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.PortfolioDto;
import com.jp.orpha.portafolio_inversion.entities.PortfolioEntity;
import com.jp.orpha.portafolio_inversion.mappers.PortfolioMapper;
import com.jp.orpha.portafolio_inversion.repositories.PortfolioRepository;
import com.jp.orpha.portafolio_inversion.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    @Override
    public List<PortfolioDto> getAllPortfolios() {
        return portfolioRepository.findAll()
                .stream()
                .map(portfolioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PortfolioDto getPortfolioById(Long id) {
        PortfolioEntity entity = portfolioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        return portfolioMapper.toDto(entity);
    }

    @Override
    public PortfolioDto createPortfolio(PortfolioDto portfolioDto) {
        PortfolioEntity entity = portfolioMapper.toEntity(portfolioDto);
        return portfolioMapper.toDto(portfolioRepository.save(entity));
    }

    @Override
    public PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto) {
        PortfolioEntity existing = portfolioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this, "id", id));
        existing.setName(portfolioDto.getName());
        return portfolioMapper.toDto(portfolioRepository.save(existing));
    }

    @Override
    public Boolean deletePortfolioById(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new NotFoundException(this, "id", id);
        }
        portfolioRepository.deleteById(id);
        return true;
    }
}