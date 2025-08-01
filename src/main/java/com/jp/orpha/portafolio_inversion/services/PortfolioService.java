package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.PortfolioDto;
import java.util.List;

public interface PortfolioService {
    List<PortfolioDto> getAllPortfolios();
    PortfolioDto getPortfolioById(Long id);
    PortfolioDto createPortfolio(PortfolioDto portfolioDto);
    PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto);
    Boolean deletePortfolioById(Long id);
}
