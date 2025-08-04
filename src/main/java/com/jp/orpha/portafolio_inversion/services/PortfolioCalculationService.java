package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.AssetWeightDto;
import com.jp.orpha.portafolio_inversion.dtos.InitialQuantityDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioEvolutionDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioPerformanceDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioStatusDto;
import com.jp.orpha.portafolio_inversion.dtos.RebalanceActionDto;

import java.time.LocalDate;
import java.util.List;

public interface PortfolioCalculationService {

    List<AssetWeightDto> calculatePortfolioWeights(Long portfolioId);

    PortfolioStatusDto getPortfolioStatus(Long portfolioId);

    List<RebalanceActionDto> rebalancePortfolio(Long portfolioId);

    List<PortfolioPerformanceDto> getPortfolioPerformance(Long portfolioId);

    List<InitialQuantityDto> calculateInitialQuantities(Long portfolioId, LocalDate initialDate, Double initialPortfolioValue);

    PortfolioEvolutionDto getPortfolioEvolution(Long portfolioId, LocalDate fechaInicio, LocalDate fechaFin);


}

