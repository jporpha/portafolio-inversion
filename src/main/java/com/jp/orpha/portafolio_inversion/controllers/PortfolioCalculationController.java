package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.AssetWeightDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioStatusDto;
import com.jp.orpha.portafolio_inversion.dtos.RebalanceActionDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioPerformanceDto;
import com.jp.orpha.portafolio_inversion.dtos.InitialQuantityDto;
import com.jp.orpha.portafolio_inversion.dtos.PortfolioEvolutionDto;
import com.jp.orpha.portafolio_inversion.services.PortfolioCalculationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio/calculations")
@RequiredArgsConstructor
@Tag(name = "Portfolio Calculations", description = "Endpoints for calculating portfolio-related information")
public class PortfolioCalculationController {

    private final PortfolioCalculationService portfolioCalculationService;

    @Operation(summary = "Get portfolio status", description = "Returns the latest value and weights of the portfolio.")
    @GetMapping("/{portfolioId}/portfolio-status")
    public ResponseEntity<PortfolioStatusDto> getPortfolioStatus(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioCalculationService.getPortfolioStatus(portfolioId));
    }

    @Operation(summary = "Calculate weights", description = "Calculate total weights of a portfolio.")
    @GetMapping("/{portfolioId}/calculate-weights")
    public ResponseEntity<List<AssetWeightDto>> calculatePortfolioWeights(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioCalculationService.calculatePortfolioWeights(portfolioId));
    }

    @Operation(summary = "Rebalances portfolio", description = "Rebalances a portfolio according to defined target weights.")
    @GetMapping("/{portfolioId}/rebalance")
    public ResponseEntity<List<RebalanceActionDto>> rebalancePortfolio(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioCalculationService.rebalancePortfolio(portfolioId));
    }

    @Operation(summary = "Obtains historical performance", description = "Obtains the historical performance of the portfolio over time.")
    @GetMapping("/{portfolioId}/performance")
    public ResponseEntity<List<PortfolioPerformanceDto>> getPortfolioPerformance(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioCalculationService.getPortfolioPerformance(portfolioId));
    }

    @Operation(summary = "Calculates the initial quantities", description = "Calculates the initial quantities (c_{i,0}) of a portfolio.")
    @GetMapping("/{portfolioId}/initial-quantities")
    public ResponseEntity<List<InitialQuantityDto>> calculateInitialQuantities(@PathVariable Long portfolioId,
                                                                               @RequestParam(name = "initialDate", required = false, defaultValue = "2022-02-15") LocalDate initialDate,
                                                                               @RequestParam(name = "initialPortfolioValue", required = false, defaultValue = "1000000000") Double initialPortfolioValue) {
        return ResponseEntity.ok(portfolioCalculationService.calculateInitialQuantities(portfolioId, initialDate, initialPortfolioValue));
    }

    @Operation(summary = "Daily evolution portfolio", description = "Daily evolution of the total value and weights of the portfolio between dates.")
    @GetMapping("/{portfolioId}/evolution")
    public ResponseEntity<PortfolioEvolutionDto> getPortfolioEvolution(@PathVariable Long portfolioId,
                                                                       @RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                       @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        PortfolioEvolutionDto evolution = portfolioCalculationService.getPortfolioEvolution(portfolioId, startDate, endDate);
        return ResponseEntity.ok(evolution);
    }
}

