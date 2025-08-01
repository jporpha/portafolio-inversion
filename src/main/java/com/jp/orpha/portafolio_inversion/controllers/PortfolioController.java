package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.PortfolioDto;
import com.jp.orpha.portafolio_inversion.services.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "Get all portfolios", description = "Returns a list of all portfolios in the system.")
    @GetMapping
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    @Operation(summary = "Get a portfolio by ID", description = "Returns the portfolio identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }

    @Operation(summary = "Create a new portfolio", description = "Creates and returns a new portfolio.")
    @PostMapping
    public ResponseEntity<PortfolioDto> createPortfolio(@RequestBody PortfolioDto portfolio) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolio));
    }

    @Operation(summary = "Update a portfolio", description = "Updates the portfolio identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDto> updatePortfolio(@PathVariable Long id,
                                                        @RequestBody PortfolioDto portfolio) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(id, portfolio));
    }

    @Operation(summary = "Delete a portfolio", description = "Deletes the portfolio identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePortfolio(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.deletePortfolioById(id));
    }
}

